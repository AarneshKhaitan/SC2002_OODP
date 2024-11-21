// boundary/LoginUI.java
package boundary;

import controller.Login.LoginController;
import controller.Login.LoginResult;
import entity.users.User;
import util.UIUtils;
/**
 * Represents the login interface for the Hospital Management System.
 * Provides functionality for user authentication and redirection to role-specific menus.
 */
public class LoginUI {
    private final LoginController loginController; // Controller for handling login logic
    private final MenuFactory menuFactory; // Factory for creating role-specific menus
    
    /**
     * Constructs a LoginUI instance.
     * Initializes the login controller and menu factory.
     */
    
    public LoginUI() {
        this.loginController = LoginController.getInstance();
        this.menuFactory = new MenuFactory();
    }
     /**
     * Displays the login menu and handles user interaction.
     * Users can either log in or exit the system.
     */
    public void display() {
        while (true) {
            UIUtils.displayHeader("Hospital Management System Login");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = UIUtils.promptForInt("Choose an option", 1, 2);

            if (choice == 2) {
                System.out.println("Thank you for using HMS. Goodbye!");
                System.exit(0);
            }

            handleLogin();
        }
    }
      /**
     * Handles the login process, including credential verification and role-based redirection.
     */
    private void handleLogin() {
        String userId = UIUtils.promptForString("Enter User ID");
        String password = UIUtils.promptForString("Enter Password");

        LoginResult result = loginController.login(userId, password);

        switch (result) {
            case REQUIRE_PASSWORD_CHANGE -> {
                if (handleFirstTimePasswordChange(userId)) {
                    UIUtils.displaySuccess("Password successfully changed!");
                    redirectToRoleMenu();
                } else {
                    UIUtils.displayError("Password change failed. Please try logging in again.");
                    loginController.logout();
                }
            }
            case SUCCESS -> redirectToRoleMenu();
            case INVALID_CREDENTIALS -> UIUtils.displayError("Invalid credentials. Please try again.");
        }
    }
    
    /**
     * Handles password change for first-time login users.
     *
     * @param userId the ID of the user whose password needs to be changed
     * @return {@code true} if the password is successfully changed, {@code false} otherwise
     */
    
    private boolean handleFirstTimePasswordChange(String userId) {
        UIUtils.displayHeader("First Time Login - Password Change Required");
        System.out.println("Password Requirements:");
        System.out.println("- Minimum 8 characters");
        System.out.println("- Must contain at least one number");
        System.out.println("- Must contain at least one uppercase letter");

        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            String newPassword = UIUtils.promptForString("Enter new password");

            if (!isValidPassword(newPassword)) {
                UIUtils.displayError("Password does not meet requirements.");
                attempts++;
                continue;
            }

            String confirmPassword = UIUtils.promptForString("Confirm new password");

            if (!newPassword.equals(confirmPassword)) {
                UIUtils.displayError("Passwords do not match.");
                attempts++;
                continue;
            }

            return loginController.changeFirstTimePassword(userId, newPassword);
        }

        UIUtils.displayError("Maximum password change attempts exceeded.");
        return false;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[A-Z].*");
    }

      /**
     * Redirects the user to their role-specific menu after successful login.
     */

    private void redirectToRoleMenu() {
        User currentUser = loginController.getCurrentUser();
        UIUtils.displaySuccess("Welcome, " + currentUser.getName() + "!");

        Menu menu = menuFactory.createMenu(currentUser);
        menu.display(currentUser);
    }
}
