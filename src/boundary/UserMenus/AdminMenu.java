//package boundary.UserMenus;
//
//
//import boundary.Menu;
//import util.UIUtils;
//import entity.users.User;
//import action.AdminAction;
//import util.InventoryManager;
//import util.StaffManager;
//import java.util.Scanner;
//
//public class AdminMenu implements Menu {
//    private final User admin;
//    private final Scanner scanner = new Scanner(System.in);
//    private final AdminAction adminAction;
//
//    public AdminMenu(User admin) {
//        this.admin = admin;
//        InventoryManager inventoryManager = new InventoryManager();
//        StaffManager staffManager = new StaffManager();
//        this.adminAction = new AdminAction(inventoryManager, staffManager);
//    }
//
//    @Override
//    public void display(User user) {
//        while (true) {
//            UIUtils.displayHeader("Administrator Menu");
//            System.out.println("1. Manage Staff");
//            System.out.println("2. Inventory Management");
//            System.out.println("3. Appointment Management");
//            System.out.println("4. Logout");
//
//            int choice = UIUtils.promptForInt("Choose an option", 1, 4);
//
//            switch (choice) {
//                case 1 -> adminAction.manageStaff();
//                case 2 -> adminAction.manageInventory();
//                case 3 -> adminAction.appointmentManagement();
//                case 4 -> {
//                    UIUtils.displaySuccess("Logging out...");
//                    return;
//                }
//            }
//        }
//    }

    // In AdminMenu.java, add this method and update the display method:




    // Update the generateReports method to include the new option:
//    private void generateReports() {
//        while (true) {
//            UIUtils.displayHeader("Generate Reports");
//            System.out.println("1. Staff Reports");
//            System.out.println("2. Patient Reports");
//            System.out.println("3. Appointment Reports");
//            System.out.println("4. Appointment Outcomes");
//            System.out.println("5. Inventory Reports");
//            System.out.println("6. Back to Main Menu");
//
//            int choice = UIUtils.promptForInt("Choose an option", 1, 6);
//
//            switch (choice) {
//                case 1 -> System.out.println("Staff Reports - To be implemented");
//                case 2 -> System.out.println("Patient Reports - To be implemented");
//                case 3 -> admin.viewAppointmentStatistics();
//                case 4 -> viewAppointmentOutcomes();
//                case 5 -> System.out.println("Inventory Reports - To be implemented");
//                case 6 -> {
//                    return;
//                }
//            }
//            UIUtils.pressEnterToContinue();
//        }
//    }
//}

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
