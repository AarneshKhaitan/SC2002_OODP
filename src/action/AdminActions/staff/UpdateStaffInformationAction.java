package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

import java.util.Scanner;

public class UpdateStaffInformationAction implements AdminAction {
    private final StaffManager staffManager;

    public UpdateStaffInformationAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID to Update: ");
        String staffId = scanner.nextLine();
        System.out.print("Enter New Name (press Enter to keep unchanged): ");
        String newName = scanner.nextLine();
        System.out.print("Enter New Gender (press Enter to keep unchanged): ");
        String newGender = scanner.nextLine();
        System.out.print("Enter New Age (press Enter to keep unchanged): ");
        String ageInput = scanner.nextLine();

        Integer newAge = ageInput.isBlank() ? null : Integer.parseInt(ageInput);

        try {
            staffManager.updateStaff(staffId, newName, newGender, newAge);
            System.out.println("Staff member updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
