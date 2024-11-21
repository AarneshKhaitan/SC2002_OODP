/**
 * Controls the login process for users, including verifying credentials, 
 * managing first-time login scenarios, and handling user sessions.
 * Utilizes {@link CSVDataLoader} to load user data and {@link CredentialManager} 
 * to handle password-related operations.
 */
package controller.Login;

import entity.users.User;
import util.CSVDataLoader;
import util.CredentialManager;
import java.util.Map;

/**
 * The {@code LoginController} class provides functionalities for user login, 
 * password management, and session handling. It ensures secure authentication 
 * and manages user data loaded from a CSV file.
 *
 * This class follows the Singleton design pattern, ensuring that only one 
 * instance of the controller is active during runtime.
 */
public class LoginController {

    /** The single instance of this class (Singleton). */
    private static LoginController instance;

    /** A map containing all users, loaded from the CSV file. */
    private final Map<String, User> users;

    /** The {@link CredentialManager} instance for handling credentials. */
    private final CredentialManager credentialManager;

    /** The currently logged-in user. */
    private User currentUser;

    /**
     * Private constructor to initialize the user data and credential manager.
     * Loads all user data from the CSV file.
     */
    private LoginController() {
        this.users = CSVDataLoader.loadAllUsers();
        this.credentialManager = CredentialManager.getInstance();
    }

    /**
     * Returns the single instance of {@code LoginController}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param userId the unique identifier of the user.
     * @param password the user's password.
     * @return a {@link LoginResult} indicating the outcome of the login attempt:
     *         {@code SUCCESS}, {@code INVALID_CREDENTIALS}, or {@code REQUIRE_PASSWORD_CHANGE}.
     */
    public LoginResult login(String userId, String password) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found in database");
            return LoginResult.INVALID_CREDENTIALS;
        }

        boolean passwordValid = credentialManager.verifyPassword(userId, password);

        if (passwordValid) {
            currentUser = user;
            boolean isFirstLogin = credentialManager.isFirstLogin(userId);

            if (isFirstLogin) {
                return LoginResult.REQUIRE_PASSWORD_CHANGE;
            }
            return LoginResult.SUCCESS;
        }
        return LoginResult.INVALID_CREDENTIALS;
    }

    /**
     * Allows a user to change their password if it is their first login.
     *
     * @param userId the unique identifier of the user.
     * @param newPassword the new password to set for the user.
     * @return {@code true} if the password was successfully changed, 
     *         {@code false} otherwise (e.g., if it is not the first login).
     */
    public boolean changeFirstTimePassword(String userId, String newPassword) {
        if (credentialManager.isFirstLogin(userId)) {
            return credentialManager.updatePassword(userId, newPassword);
        }
        return false;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the {@link User} object representing the currently logged-in user, 
     *         or {@code null} if no user is logged in.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs out the currently logged-in user by clearing the session data.
     */
    public void logout() {
        currentUser = null;
    }
}
