package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;
import util.UIUtils;

import java.util.Scanner;

public class RemoveStaffMemberAction implements AdminAction {
    private final StaffManager staffManager;

    public RemoveStaffMemberAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

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
