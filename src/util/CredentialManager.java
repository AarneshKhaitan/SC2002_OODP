/**
 * Manages user credentials, including authentication, password management, 
 * and first login tracking. This class loads credentials from a CSV file, 
 * verifies passwords, and updates user credentials when necessary.
 */
package util;

import entity.users.User;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The {@code CredentialManager} class provides functionalities for managing user credentials.
 * It includes methods for verifying passwords, tracking first login status, and updating passwords.
 * The credentials are stored and maintained in a CSV file.
 *
 * This class follows the Singleton design pattern to ensure only one instance is active.
 */
public class CredentialManager {

    /** File path for storing user credentials. */
    private static final String CREDENTIALS_FILE = "data/credentials.csv";

    /** The single instance of the {@code CredentialManager} (Singleton). */
    private static CredentialManager instance;

    /** A map of user credentials, keyed by user ID. */
    private final Map<String, UserCredential> userCredentials;

    /**
     * A nested class to represent user credentials, including the hashed password
     * and a flag for whether this is the user's first login.
     */
    private static class UserCredential {
        /** The hashed password of the user. */
        String passwordHash;

        /** Indicates whether it is the user's first login. */
        boolean isFirstLogin;

        /**
         * Constructs a new {@code UserCredential}.
         *
         * @param passwordHash the hashed password of the user.
         * @param isFirstLogin {@code true} if it is the user's first login; otherwise {@code false}.
         */
        UserCredential(String passwordHash, boolean isFirstLogin) {
            this.passwordHash = passwordHash;
            this.isFirstLogin = isFirstLogin;
        }
    }

    /**
     * Private constructor to initialize the credentials from the CSV file.
     */
    private CredentialManager() {
        userCredentials = new HashMap<>();
        initializeCredentials();
    }

    /**
     * Returns the single instance of {@code CredentialManager}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static CredentialManager getInstance() {
        if (instance == null) {
            instance = new CredentialManager();
        }
        return instance;
    }

    /**
     * Initializes credentials by loading them from the CSV file.
     * If the file does not exist, it creates a new file with default credentials.
     */
    private void initializeCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) {
            createCredentialsFile();
        }
        loadCredentials();
    }

    /**
     * Creates the credentials file with default passwords for all users.
     */
    private void createCredentialsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CREDENTIALS_FILE))) {
            writer.println("UserId,PasswordHash,IsFirstLogin");
            Map<String, User> users = CSVDataLoader.loadAllUsers();
            for (String userId : users.keySet()) {
                writer.printf("%s,%s,true%n", userId, hashPassword("password"));
            }
        } catch (IOException e) {
            System.err.println("Error creating credentials file: " + e.getMessage());
        }
    }

    /**
     * Loads credentials from the CSV file into memory.
     */
    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String userId = data[0].trim();
                    String passwordHash = data[1].trim();
                    boolean isFirstLogin = Boolean.parseBoolean(data[2].trim());
                    userCredentials.put(userId, new UserCredential(passwordHash, isFirstLogin));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading credentials: " + e.getMessage());
        }
    }

    /**
     * Hashes a plain-text password using the SHA-256 algorithm.
     *
     * @param password the plain-text password to hash.
     * @return the hashed password as a hexadecimal string.
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies if the provided password matches the stored hashed password for a user.
     *
     * @param userId the unique identifier of the user.
     * @param password the plain-text password to verify.
     * @return {@code true} if the password matches; {@code false} otherwise.
     */
    public boolean verifyPassword(String userId, String password) {
        UserCredential credential = userCredentials.get(userId);
        return credential != null && credential.passwordHash.equals(hashPassword(password));
    }

    /**
     * Checks if a user is logging in for the first time.
     *
     * @param userId the unique identifier of the user.
     * @return {@code true} if it is the user's first login; {@code false} otherwise.
     */
    public boolean isFirstLogin(String userId) {
        UserCredential credential = userCredentials.get(userId);
        return credential != null && credential.isFirstLogin;
    }

    /**
     * Updates the password for a user and sets the first login flag to {@code false}.
     *
     * @param userId the unique identifier of the user.
     * @param newPassword the new password to set.
     * @return {@code true} if the password was successfully updated; {@code false} otherwise.
     */
    public boolean updatePassword(String userId, String newPassword) {
        if (!userCredentials.containsKey(userId)) return false;
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
                lines.add(reader.readLine()); // Add header
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].trim().equals(userId)) {
                        lines.add(String.format("%s,%s,false", userId, hashPassword(newPassword)));
                    } else {
                        lines.add(line);
                    }
                }
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(CREDENTIALS_FILE))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
            UserCredential credential = userCredentials.get(userId);
            credential.passwordHash = hashPassword(newPassword);
            credential.isFirstLogin = false;
            return true;
        } catch (IOException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }
}
