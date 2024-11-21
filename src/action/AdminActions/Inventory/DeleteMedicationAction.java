package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the deletion of medications from the inventory.
 * <p>
 * This action allows administrators to remove a specific medication
 * from the inventory by entering its name. It validates the existence
 * of the medication before deletion and updates the inventory after
 * the operation.
 * </p>
 */
public class DeleteMedicationAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs a {@code DeleteMedicationAction} instance.
     *
     * @param inventoryManager the inventory manager used to manage medication data
     */
    public DeleteMedicationAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to delete a medication from the inventory.
     * <p>
     * Prompts the administrator to enter the name of the medication to delete.
     * If the medication is found, it is removed from the inventory and the
     * updated inventory is saved. If the medication is not found, a message
     * is displayed to inform the administrator.
     * </p>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name to Delete: ");
        String medicationName = scanner.nextLine();

        List<Medication> medications = inventoryManager.getMedications();
        for (Medication medication : medications) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                medications.remove(medication);
                InventoryManager.saveMedications(medications);
                System.out.println("Medication deleted successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }
}
