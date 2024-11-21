package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import entity.users.User;
import util.StaffManager;
import util.UIUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Provides functionality to filter and display staff members based on various criteria.
 * <p>
 * This action allows administrators to filter staff by role, gender, or age range,
 * as well as display all staff members. The filtered results are displayed in a
 * formatted output, and if no staff match the criteria, a corresponding message is shown.
 * </p>
 */
public class FilterAndDisplayStaffAction implements AdminAction {
    private final StaffManager staffManager;

    /**
     * Constructs a {@code FilterAndDisplayStaffAction} instance.
     *
     * @param staffManager the staff manager used to manage and query staff data
     */
    public FilterAndDisplayStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    /**
     * Executes the action to filter and display staff members.
     * <p>
     * Prompts the administrator to choose a filtering criterion, retrieves the filtered list
     * of staff members based on the selected criterion, and displays the results. If no
     * staff members match the criteria, a message is displayed. The administrator can
     * also choose to view all staff or return to the main menu.
     * </p>
     * <ul>
     *     <li>Filter options:
     *         <ul>
     *             <li>By Role (Doctor, Pharmacist, Administrator)</li>
     *             <li>By Gender (Male, Female)</li>
     *             <li>By Age Range</li>
     *         </ul>
     *     </li>
     *     <li>Displays all staff when selected.</li>
     * </ul>
     */
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
