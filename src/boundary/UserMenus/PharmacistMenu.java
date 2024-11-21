

package boundary.UserMenus;

import action.PharmacistActions.*;
import boundary.Menu;
import entity.users.User;
import util.InventoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * Represents the menu interface for a Pharmacist user.
 * Provides options for managing prescriptions, inventory, and replenishment requests.
 */
public class PharmacistMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in); // Scanner for user input
    private final User pharmacist; // The pharmacist user
    private final Map<String, PharmacistAction> actions; // Mapping of menu options to actions
      /**
     * Constructs a PharmacistMenu instance.
     *
     * @param pharmacist the pharmacist user for whom the menu is being displayed
     */
    public PharmacistMenu(User pharmacist) {
        this.pharmacist = pharmacist;
        this.actions = new HashMap<>();
        // Initialize and register all pharmacist actions
        InventoryManager inventoryManager = new InventoryManager();
        actions.put("1", new ViewPrescriptionsAction());
        actions.put("2", new UpdatePrescriptionStatusAction(inventoryManager));
        actions.put("3", new ViewInventoryAction(inventoryManager));
        actions.put("4", new SubmitReplenishmentRequestAction(inventoryManager));

    }
      /**
     * Displays the Pharmacist menu and handles user interaction.
     * Allows the pharmacist to perform actions such as viewing prescriptions, updating their status,
     * viewing inventory, and submitting replenishment requests.
     *
     * @param user the pharmacist user
     */
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
            // Handle logout
            if ("5".equals(choice)) {
                System.out.println("Logging out...");
                return;
            }
            // Execute the selected action
            PharmacistAction action = actions.get(choice);
            if (action != null) {
                action.execute(pharmacist);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
