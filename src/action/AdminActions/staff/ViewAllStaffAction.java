package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

/**
 * Displays a list of all staff members in the system.
 * <p>
 * This action retrieves all staff members from the staff manager and prints their details.
 * It ensures that all staff records are accessible in a formatted and readable output.
 * </p>
 */
public class ViewAllStaffAction implements AdminAction {
    private final StaffManager staffManager;

    /**
     * Constructs a {@code ViewAllStaffAction} instance.
     *
     * @param staffManager the staff manager used to retrieve staff data
     */
    public ViewAllStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    /**
     * Executes the action to display all staff members.
     * <p>
     * Retrieves a list of all staff members from the system and displays their details.
     * Each staff member's information is printed in a formatted manner for easy readability.
     * </p>
     */
    @Override
    public void execute() {
        System.out.println("\n=== All Staff Members ===");
        staffManager.getStaff().values().forEach(System.out::println);
    }
}
