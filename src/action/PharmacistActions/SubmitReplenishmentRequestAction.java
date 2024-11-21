package action.PharmacistActions;

import entity.users.User;
import entity.Medications.ReplenishmentRequest;
import util.InventoryManager;

import java.util.Scanner;

public class SubmitReplenishmentRequestAction implements PharmacistAction {
    private final InventoryManager inventoryManager;

    public SubmitReplenishmentRequestAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute(User pharmacist) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter Quantity to Replenish: ");
        int quantity = scanner.nextInt();

        ReplenishmentRequest request = new ReplenishmentRequest(medicationName, quantity, pharmacist.getUserId());
        inventoryManager.addReplenishmentRequest(request);
        System.out.println("Replenishment request submitted.");
    }
}
