package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import entity.users.User;
import util.StaffManager;
import util.UIUtils;

import java.util.List;
import java.util.Scanner;

public class FilterAndDisplayStaffAction implements AdminAction {
    private final StaffManager staffManager;

    public FilterAndDisplayStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Filter Staff ===");
        System.out.println("1. Filter by Role");
        System.out.println("2. Filter by Gender");
        System.out.println("3. Filter by Age Range");
        System.out.println("4. Display All Staff");
        System.out.println("5. Back to Main Menu");

        int choice = UIUtils.promptForInt("Choose an option", 1, 5);

        List<User> filteredStaff = null;

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Role (DOCTOR/PHARMACIST/ADMINISTRATOR): ");
                String role = scanner.nextLine().toUpperCase();
                filteredStaff = staffManager.filterStaffByRole(role);
            }
            case 2 -> {
                System.out.print("Enter Gender (Male/Female): ");
                String gender = scanner.nextLine();
                filteredStaff = staffManager.filterStaffByGender(gender);
            }
            case 3 -> {
                int minAge = UIUtils.promptForInt("Enter Minimum Age", 0, Integer.MAX_VALUE);
                int maxAge = UIUtils.promptForInt("Enter Maximum Age", minAge, Integer.MAX_VALUE);
                filteredStaff = staffManager.filterStaffByAgeRange(minAge, maxAge);
            }
            case 4 -> {
                filteredStaff = List.copyOf(staffManager.getStaff().values());
            }
            case 5 -> {
                System.out.println("Returning to menu...");
                return;
            }
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }

        if (filteredStaff == null || filteredStaff.isEmpty()) {
            System.out.println("\nNo staff members match the given criteria.");
        } else {
            System.out.println("\nFiltered Staff:");
            for (User user : filteredStaff) {
                System.out.println(user);
            }
        }

        UIUtils.pressEnterToContinue();
    }
}
