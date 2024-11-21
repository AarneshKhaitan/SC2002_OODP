package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

public class ViewAllStaffAction implements AdminAction {
    private final StaffManager staffManager;

    public ViewAllStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    @Override
    public void execute() {
        System.out.println("\n=== All Staff Members ===");
        staffManager.getStaff().values().forEach(System.out::println);
    }
}
