package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

/**
 * Handles updating the stock level for a specific medication in the inventory.
 * <p>
 * This action allows administrators to set a new stock level for a medication.
 * It validates the medication's existence in the inventory before updating its stock level.
 * </p>
 */
public class UpdateStockAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs an {@code UpdateStockAction} instance.
     *
     * @param inventoryManager the inventory manager used to manage medication data
     */
    public UpdateStockAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to update the stock level of a medication.
     * <p>
     * Prompts the administrator to enter the name of the medication and the desired stock level.
     * If the medication is found, its stock level is updated and the inventory is saved.
     * If the medication is not found, an appropriate message is displayed.
     * </p>
     */
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
