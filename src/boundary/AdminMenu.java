package boundary;

import action.AdminAction;
import entity.User;
import util.InventoryManager;

import java.util.Scanner;

public class AdminMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final AdminAction adminAction;

    public AdminMenu() {
        // Initialize the InventoryManager and Action class
        InventoryManager inventoryManager = new InventoryManager();
        this.adminAction = new AdminAction(inventoryManager);
    }

    @Override
    public void display(User user) {
        while (true) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. Manage Staff");
            System.out.println("2. View System Statistics");
            System.out.println("3. Manage Inventory");
            System.out.println("4. View Appointment Records");
            System.out.println("5. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> manageStaff();
                case "2" -> viewStatistics();
                case "3" -> manageInventory();
                case "4" -> viewAppointments();
                case "5" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageStaff() {
        // Placeholder for staff management logic
        System.out.println("Managing staff... [To be implemented]");
    }

    private void viewStatistics() {
        // Placeholder for system statistics logic
        System.out.println("Viewing statistics... [To be implemented]");
    }

    private void manageInventory() {
        while (true) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Update Medication Stock");
            System.out.println("3. Approve Replenishment Requests");
            System.out.println("4. Back to Main Menu");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> adminAction.viewInventory();
                case "2" -> adminAction.updateStock();
                case "3" -> adminAction.approveReplenishmentRequests();
                case "4" -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAppointments() {
        // Placeholder for appointment handling logic
        System.out.println("Viewing appointments... [To be implemented]");
    }
}
