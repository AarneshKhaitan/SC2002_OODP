package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;

/**
 * Displays the current inventory of medications.
 * <p>
 * This action retrieves the list of medications from the inventory and
 * displays their details, including name, current stock level, and
 * low stock alert level. If the inventory is empty, it informs the administrator.
 * </p>
 */
public class ViewInventoryAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs a {@code ViewInventoryAction} instance.
     *
     * @param inventoryManager the inventory manager used to access medication data
     */
    public ViewInventoryAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to view the inventory of medications.
     * <p>
     * Retrieves the list of medications from the inventory manager. If medications are available,
     * it displays their details in a formatted output. If no medications are found, a message
     * is displayed to inform the administrator.
     * </p>
     */
    @Override
    public void execute() {
        System.out.println("=== Medication Inventory ===");
        List<Medication> medications = inventoryManager.getMedications();

        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }

        for (Medication medication : medications) {
            System.out.printf("Name: %s | Stock: %d | Low Stock Alert: %d%n",
                    medication.getName(),
                    medication.getCurrentStock(),
                    medication.getLowStockAlert());
        }
    }
}
