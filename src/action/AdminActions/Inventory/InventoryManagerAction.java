package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import util.InventoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryManagerAction implements AdminAction {
    private final Map<Integer, AdminAction> actions;

    public InventoryManagerAction() {
        InventoryManager inventoryManager = new InventoryManager();
        actions = new HashMap<>();
        actions.put(1, new ViewInventoryAction(inventoryManager));
        actions.put(2, new UpdateStockAction(inventoryManager));
        actions.put(3, new UpdateLowStockAlertAction(inventoryManager));
        actions.put(4, new AddMedicationAction(inventoryManager));
        actions.put(5, new DeleteMedicationAction(inventoryManager));
        actions.put(6, new ApproveReplenishmentRequestsAction(inventoryManager));
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Inventory Management ===");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Stock");
            System.out.println("3. Update Low Stock Alert Level");
            System.out.println("4. Add New Medication");
            System.out.println("5. Delete Medication");
            System.out.println("6. Approve Replenishment Requests");
            System.out.println("7. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 7) {
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
