package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;
import util.UIUtils;

import java.util.Scanner;

/**
 * Handles the removal of a staff member from the system.
 * <p>
 * This action allows administrators to remove a staff member by entering their staff ID.
 * It validates the input and uses the staff manager to delete the staff member. If the
 * staff member does not exist, an error message is displayed.
 * </p>
 */
public class RemoveStaffMemberAction implements AdminAction {
    private final StaffManager staffManager;

    /**
     * Constructs a {@code RemoveStaffMemberAction} instance.
     *
     * @param staffManager the staff manager used to manage staff data
     */
    public RemoveStaffMemberAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    /**
     * Executes the action to remove a staff member.
     * <p>
     * Prompts the administrator to enter the staff ID of the member to be removed.
     * If the staff member exists, they are removed from the system, and a success
     * message is displayed. If the staff member does not exist, an error message
     * is shown. The action handles errors gracefully and allows the administrator
     * to proceed after confirmation.
     * </p>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Staff ID to Remove: ");
        String staffId = scanner.nextLine();

        try {
            staffManager.removeStaff(staffId);
            UIUtils.displaySuccess("Staff member removed successfully.");
        } catch (IllegalArgumentException e) {
            UIUtils.displayError(e.getMessage());
        }
        UIUtils.pressEnterToContinue();
    }
}
