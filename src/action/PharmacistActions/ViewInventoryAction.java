package action.PharmacistActions;

import entity.users.User;
import entity.Medications.Medication;
import util.InventoryManager;

/**
 * Represents an action to view the inventory details of the pharmacy.
 * 
 * This class allows pharmacists to access an overview of the pharmacy's inventory,
 * including details such as:
 * <ul>
 *   <li>Medication names</li>
 *   <li>Current stock levels</li>
 *   <li>Low stock alert thresholds</li>
 * </ul>
 * It interacts with the {@link InventoryManager} to fetch the inventory details.
 */
public class ViewInventoryAction implements PharmacistAction {

    /**
     * Manager for handling inventory operations.
     */
    private final InventoryManager inventoryManager;

     /**
     * Constructs an instance of {@code ViewInventoryAction}.
     * 
     * @param inventoryManager The inventory manager used to retrieve inventory details.
     */
    public ViewInventoryAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

     /**
     * Executes the action to display the current inventory details for the pharmacy.
     * 
     * The inventory details include:
     * <ul>
     *   <li>Medication names</li>
     *   <li>Current stock levels</li>
     *   <li>Low stock alert thresholds</li>
     * </ul>
     * The data is retrieved from the {@link InventoryManager} and displayed in a formatted structure.
     *
     * @param pharmacist The {@link User} object representing the pharmacist viewing the inventory.
     */
    @Override
    public void execute(User pharmacist) {
        System.out.println("=== Medication Inventory ===");

         // Display inventory details for each medication
        for (Medication medication : inventoryManager.getMedications()) {
            System.out.printf("%s - Stock: %d, Low Stock Alert: %d%n",
                    medication.getName(),
                    medication.getCurrentStock(),
                    medication.getLowStockAlert());
        }
    }
}
