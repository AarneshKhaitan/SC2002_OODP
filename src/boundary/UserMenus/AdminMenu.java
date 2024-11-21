
package boundary.UserMenus;

import action.AdminActions.AdminAction;
import action.AdminActions.Inventory.InventoryManagerAction;
import action.AdminActions.staff.StaffManagerAction;
import action.AdminActions.appointment.AppointmentManagerAction;
import entity.users.User;
import boundary.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the menu interface for an Administrator user.
 * Provides options for inventory, staff, and appointment management.
 */

public class AdminMenu implements Menu {
    /**
     * Constructs an AdminMenu instance.
     *
     * @param admin the administrator user for whom the menu is being displayed
     */
    private final User admin;
    private final Map<Integer, AdminAction> actions;

    public AdminMenu(User admin) {
        // Initializes the admin menu with the specified administrator.
        this.admin = admin;

        // Initialize all admin actions
        this.actions = new HashMap<>();
        this.actions.put(1, new InventoryManagerAction());
        this.actions.put(2, new StaffManagerAction());
        this.actions.put(3, new AppointmentManagerAction());
    }

    /**
     * Displays the Administrator menu with options to manage inventory, staff, and appointments.
     * Allows the administrator to perform actions based on their selection.
     *
     * @param user the administrator user
     */

    @Override
    public void display(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Displays menu options and executes the selected action.
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. Inventory Management");
            System.out.println("2. Staff Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Logout");

            // Prompt for user choice
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            // Handle Logout
            if (choice == 4) {
                System.out.println("Logging out...");
                return;
            }

            // Execute selected action
            AdminAction action = actions.get(choice);
            if (action != null) {
                action.execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
