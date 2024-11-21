package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

import java.util.Scanner;

/**
 * Handles updating information for an existing staff member in the system.
 * <p>
 * This action allows administrators to update the name, gender, or age of a staff member.
 * It validates the staff ID and provides options to keep certain fields unchanged.
 * </p>
 */
public class UpdateStaffInformationAction implements AdminAction {
    private final StaffManager staffManager;

    /**
     * Constructs an {@code UpdateStaffInformationAction} instance.
     *
     * @param staffManager the staff manager used to manage and update staff data
     */
    public UpdateStaffInformationAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    /**
     * Executes the action to update a staff member's information.
     * <p>
     * Prompts the administrator to enter the staff ID of the member to update, along with
     * the new values for name, gender, and age. Allows the administrator to leave a field
     * unchanged by pressing Enter. Updates the staff information and handles errors if
     * the staff member does not exist or the input is invalid.
     * </p>
     */
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
