package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import util.StaffManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StaffManagerAction implements AdminAction {
    private final Map<Integer, AdminAction> actions;
    private final StaffManager staffManager;
    public StaffManagerAction() {
        this.staffManager = new StaffManager();

        actions = new HashMap<>();
        actions.put(1, new ViewAllStaffAction(staffManager));
        actions.put(2, new AddStaffAction(staffManager));
        actions.put(3, new UpdateStaffInformationAction(staffManager));
        actions.put(4, new RemoveStaffMemberAction(staffManager));
        actions.put(5, new FilterAndDisplayStaffAction(staffManager));
    }

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
