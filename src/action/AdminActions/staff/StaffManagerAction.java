package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Provides a menu-driven interface for managing staff members in the system.
 * <p>
 * This action enables administrators to perform various operations on staff members,
 * such as viewing all staff, adding new staff, updating staff information, removing staff,
 * and filtering staff based on specific criteria.
 * </p>
 */
public class StaffManagerAction implements AdminAction {
    private final Map<Integer, AdminAction> actions;
    private final StaffManager staffManager;

    /**
     * Constructs a {@code StaffManagerAction} instance and initializes the available actions.
     */
    public StaffManagerAction() {
        this.staffManager = new StaffManager();

        actions = new HashMap<>();
        actions.put(1, new ViewAllStaffAction(staffManager));
        actions.put(2, new AddStaffAction(staffManager));
        actions.put(3, new UpdateStaffInformationAction(staffManager));
        actions.put(4, new RemoveStaffMemberAction(staffManager));
        actions.put(5, new FilterAndDisplayStaffAction(staffManager));
    }

    /**
     * Executes the staff management menu and handles user input.
     * <p>
     * Displays a menu of available staff management options, processes the selected action,
     * and loops until the administrator chooses to return to the main menu. If an invalid
     * option is selected, the user is prompted to try again.
     * </p>
     * <ul>
     *     <li>Options available:
     *         <ul>
     *             <li>View all staff</li>
     *             <li>Add new staff</li>
     *             <li>Update staff information</li>
     *             <li>Remove a staff member</li>
     *             <li>Filter and display staff</li>
     *             <li>Back to main menu</li>
     *         </ul>
     *     </li>
     * </ul>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Staff Management ===");
            System.out.println("1. View All Staff");
            System.out.println("2. Add New Staff");
            System.out.println("3. Update Staff Information");
            System.out.println("4. Remove Staff Member");
            System.out.println("5. Filter and Display Staff");
            System.out.println("6. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 6) {
                System.out.println("Returning to main menu...");
                return;
            }

            AdminAction action = actions.get(choice);
            if (action != null) {
                action.execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
