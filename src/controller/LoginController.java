package controller;

import entity.User;
import util.CSVDataLoader;
import util.CredentialManager;
import java.util.Map;

public class LoginController {
    private static LoginController instance;
    private final Map<String, User> users;
    private final CredentialManager credentialManager;
    private User currentUser;

    private LoginController() {
        this.users = CSVDataLoader.loadAllUsers();
        this.credentialManager = CredentialManager.getInstance();
    }

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

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

    public boolean changeFirstTimePassword(String userId, String newPassword) {
        if (credentialManager.isFirstLogin(userId)) {
            return credentialManager.updatePassword(userId, newPassword);
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}