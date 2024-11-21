package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

public class UpdateStockAction implements AdminAction {
    private final InventoryManager inventoryManager;

    public UpdateStockAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter New Stock Level: ");
        int newStock = scanner.nextInt();

        List<Medication> medications = inventoryManager.getMedications();
        for (Medication medication : medications) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                medication.increaseStock(newStock - medication.getCurrentStock());
                InventoryManager.saveMedications(medications);
                System.out.println("Stock updated successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }
}
