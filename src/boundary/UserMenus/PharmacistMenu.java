//package boundary.UserMenus;
//
//import action.PharmacistAction;
//import boundary.Menu;
//import entity.users.User;
//
//
//import java.util.Scanner;
//
//public class PharmacistMenu implements Menu {
//    private final Scanner scanner = new Scanner(System.in);
//    private final PharmacistAction pharmacistAction;
//    private final User pharmacist;
//
//
//    public PharmacistMenu(User user) {
//        this.pharmacistAction = new PharmacistAction();
//        this.pharmacist = user;
//    }
//
//    @Override
//    public void display(User user) {
//        while (true) {
//            System.out.println("\n=== Pharmacist Menu ===");
//            System.out.println("1. View Prescriptions");
//            System.out.println("2. Update Prescription Status");
//            System.out.println("3. View Medication Inventory");
//            System.out.println("4. Request Medication Replenishment");
//            System.out.println("5. Logout");
//
//            System.out.print("Choose an option: ");
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> pharmacistAction.viewPrescriptions();
//                case "2" -> pharmacistAction.updatePrescriptionStatus();
//                case "3" -> pharmacistAction.viewInventory();
//                case "4" -> pharmacistAction.submitReplenishmentRequest();
//                case "5" -> {
//                    System.out.println("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }
//}

package boundary.UserMenus;

import action.PharmacistActions.*;
import boundary.Menu;
import entity.users.User;
import util.InventoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PharmacistMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final User pharmacist;
    private final Map<String, PharmacistAction> actions;

    public PharmacistMenu(User pharmacist) {
        this.pharmacist = pharmacist;
        this.actions = new HashMap<>();
        // Initialize and register all pharmacist actions
        InventoryManager inventoryManager = new InventoryManager();
        actions.put("1", new ViewPrescriptionsAction());
        actions.put("2", new UpdatePrescriptionStatusAction(inventoryManager));
        actions.put("3", new ViewInventoryAction(inventoryManager));
        actions.put("4", new SubmitReplenishmentRequestAction(inventoryManager));

    }

    @Override
    public void display(User user) {
        while (true) {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Prescriptions");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Request Medication Replenishment");
            System.out.println("5. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if ("5".equals(choice)) {
                System.out.println("Logging out...");
                return;
            }

            PharmacistAction action = actions.get(choice);
            if (action != null) {
                action.execute(pharmacist);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
