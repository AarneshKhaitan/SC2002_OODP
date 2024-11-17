package action;

import entity.Medication;
import entity.ReplenishmentRequest;
import util.InventoryManager;

import java.util.Scanner;

public class PharmacistAction {
    private final InventoryManager inventoryManager;

    public PharmacistAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void viewInventory() {
        System.out.println("=== Medication Inventory ===");
        for (Medication medication : inventoryManager.getMedications()) {
            System.out.printf("%s - Stock: %d, Low Stock Alert: %d%n",
                    medication.getName(),
                    medication.getCurrentStock(),
                    medication.getLowStockAlert());
        }
    }

    public void submitReplenishmentRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter Quantity to Replenish: ");
        int quantity = scanner.nextInt();

        ReplenishmentRequest request = new ReplenishmentRequest(medicationName, quantity, "PharmacistID");
        inventoryManager.addReplenishmentRequest(request);
        System.out.println("Replenishment request submitted.");
    }
}
