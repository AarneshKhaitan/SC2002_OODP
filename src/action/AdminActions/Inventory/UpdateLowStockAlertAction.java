package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

public class UpdateLowStockAlertAction implements AdminAction {
    private final InventoryManager inventoryManager;

    public UpdateLowStockAlertAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();

        List<Medication> medications = inventoryManager.getMedications();
        for (Medication medication : medications) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                System.out.print("Enter New Low Stock Alert Level: ");
                int newLowStockAlert = scanner.nextInt();
                medication.setLowStockAlert(newLowStockAlert);
                InventoryManager.saveMedications(medications);
                System.out.println("Low stock alert level updated successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }
}
