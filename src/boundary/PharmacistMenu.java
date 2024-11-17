package boundary;

import action.PharmacistAction;
import entity.User;
import util.InventoryManager;

import java.util.Scanner;

public class PharmacistMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final PharmacistAction pharmacistAction;

    public PharmacistMenu() {
        // Initialize the InventoryManager and Action class
        InventoryManager inventoryManager = new InventoryManager();
        this.pharmacistAction = new PharmacistAction(inventoryManager);
    }

    @Override
    public void display(User user) {
        while (true) {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Prescriptions");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Request Medication Replenishment");
            System.out.println("5. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewPrescriptions();
                case "2" -> updatePrescriptionStatus();
                case "3" -> pharmacistAction.viewInventory();
                case "4" -> pharmacistAction.submitReplenishmentRequest();
                case "5" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewPrescriptions() {
        // Placeholder for prescription handling logic
        System.out.println("Viewing prescriptions... [To be implemented]");
    }

    private void updatePrescriptionStatus() {
        // Placeholder for prescription update logic
        System.out.println("Updating prescription status... [To be implemented]");
    }
}
