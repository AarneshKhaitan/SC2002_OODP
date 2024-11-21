package action.PharmacistActions;

import entity.users.User;
import entity.Medications.Medication;
import util.InventoryManager;

/**
 * Handles viewing the inventory details for the pharmacy.
 * Provides information on current stock levels, expiration dates, and available medications.
 */
public class ViewInventoryAction implements PharmacistAction {
    private final InventoryManager inventoryManager;

    public ViewInventoryAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute(User pharmacist) {
        System.out.println("=== Medication Inventory ===");
        for (Medication medication : inventoryManager.getMedications()) {
            System.out.printf("%s - Stock: %d, Low Stock Alert: %d%n",
                    medication.getName(),
                    medication.getCurrentStock(),
                    medication.getLowStockAlert());
        }
    }
}
