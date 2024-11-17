package util;

import entity.User;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CredentialManager {
    private static final String CREDENTIALS_FILE = "data/credentials.csv";
    private static CredentialManager instance;
    private final Map<String, UserCredential> userCredentials;

    private static class UserCredential {
        String passwordHash;
        boolean isFirstLogin;

        UserCredential(String passwordHash, boolean isFirstLogin) {
            this.passwordHash = passwordHash;
            this.isFirstLogin = isFirstLogin;
        }
    }

    private CredentialManager() {
        userCredentials = new HashMap<>();
        initializeCredentials();
    }

    public static CredentialManager getInstance() {
        if (instance == null) {
            instance = new CredentialManager();
        }
        return instance;
    }

    private void initializeCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) {
            createCredentialsFile();
        }
        loadCredentials();
    }

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

    public boolean verifyPassword(String userId, String password) {
        UserCredential credential = userCredentials.get(userId);
        return credential != null && credential.passwordHash.equals(hashPassword(password));
    }

    public boolean isFirstLogin(String userId) {
        UserCredential credential = userCredentials.get(userId);
        return credential != null && credential.isFirstLogin;
    }

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
