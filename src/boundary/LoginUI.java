package boundary;

import util.InventoryManager;
import controller.LoginController;
import controller.LoginResult;
import entity.User;
import java.util.Scanner;

public class LoginUI {
    private final LoginController loginController;
    private final Scanner scanner;
    private final MenuFactory menuFactory;

    public LoginUI() {
        this.loginController = LoginController.getInstance();
        this.scanner = new Scanner(System.in);
        this.menuFactory = new MenuFactory();
    }

    public void display() {
        while (true) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> {
                    System.out.println("Thank you for using HMS. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        LoginResult result = loginController.login(userId, password);

        switch (result) {
            case REQUIRE_PASSWORD_CHANGE -> {
                if (handleFirstTimePasswordChange(userId)) {
                    System.out.println("Password successfully changed!");
                    redirectToRoleMenu();
                } else {
                    System.out.println("Password change failed. Please try logging in again.");
                    loginController.logout();
                }
            }
            case SUCCESS -> redirectToRoleMenu();
            case INVALID_CREDENTIALS -> System.out.println("Invalid credentials. Please try again.");
        }
    }

    private boolean handleFirstTimePasswordChange(String userId) {
        System.out.println("\nThis is your first login. You must change your password.");
        System.out.println("Password Requirements:");
        System.out.println("- Minimum 8 characters");
        System.out.println("- Must contain at least one number");
        System.out.println("- Must contain at least one uppercase letter");

        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.print("\nEnter new password: ");
            String newPassword = scanner.nextLine();

            if (!isValidPassword(newPassword)) {
                System.out.println("Password does not meet requirements. Please try again.");
                attempts++;
                continue;
            }

            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
                attempts++;
                continue;
            }

            return loginController.changeFirstTimePassword(userId, newPassword);
        }

        System.out.println("Maximum password change attempts exceeded.");
        return false;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[A-Z].*");
    }

    private void redirectToRoleMenu() {
        User currentUser = loginController.getCurrentUser();
        System.out.println("Login successful! Welcome, " + currentUser.getName());

        Menu menu = menuFactory.createMenu(currentUser.getRole());
        menu.display(currentUser);
    }
}