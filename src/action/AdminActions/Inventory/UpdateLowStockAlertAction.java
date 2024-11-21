package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

/**
 * Handles updating the low stock alert level for a specific medication in the inventory.
 * <p>
 * This action allows administrators to set a new low stock alert level for a medication.
 * It ensures that the medication exists in the inventory before updating its alert level.
 * </p>
 */
public class UpdateLowStockAlertAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs an {@code UpdateLowStockAlertAction} instance.
     *
     * @param inventoryManager the inventory manager used to manage medication data
     */
    public UpdateLowStockAlertAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to update the low stock alert level for a medication.
     * <p>
     * Prompts the administrator to enter the medication name and validates its existence
     * in the inventory. If the medication is found, the administrator is prompted to enter
     * a new low stock alert level, which is then updated and saved in the inventory.
     * If the medication is not found, an appropriate message is displayed.
     * </p>
     */
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
