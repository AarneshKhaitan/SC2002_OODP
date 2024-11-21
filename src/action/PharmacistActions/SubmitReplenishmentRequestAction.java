package action.PharmacistActions;

import entity.users.User;
import entity.Medications.ReplenishmentRequest;
import util.InventoryManager;

import java.util.Scanner;

/**
 * Represents an action to submit requests for inventory replenishment.
 * 
 * This class allows pharmacists to identify low-stock medications and submit a request
 * for replenishment. It interacts with the {@link InventoryManager} to process and record
 * the replenishment requests.
 */
public class SubmitReplenishmentRequestAction implements PharmacistAction {

    /**
     * Manager for handling inventory operations, including replenishment requests.
     */
    private final InventoryManager inventoryManager;

    /**
     * Constructs an instance of {@code SubmitReplenishmentRequestAction}.
     * 
     * @param inventoryManager The manager used to process replenishment requests.
     */
    public SubmitReplenishmentRequestAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

     /**
     * Executes the action to submit a replenishment request for low-stock medications.
     * 
     * The steps include:
     * <ul>
     *   <li>Prompting the pharmacist to enter the medication name and quantity.</li>
     *   <li>Creating a {@link ReplenishmentRequest} object with the entered data.</li>
     *   <li>Submitting the request using the {@link InventoryManager}.</li>
     *   <li>Displaying a confirmation message to the pharmacist.</li>
     * </ul>
     *
     * @param pharmacist The {@link User} object representing the pharmacist submitting the request.
     */
    @Override
    public void execute(User pharmacist) {
        Scanner scanner = new Scanner(System.in);

         // Prompt for medication details
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter Quantity to Replenish: ");
        int quantity = scanner.nextInt();

        // Create and submit the replenishment request
        ReplenishmentRequest request = new ReplenishmentRequest(medicationName, quantity, pharmacist.getUserId());
        inventoryManager.addReplenishmentRequest(request);

        // Confirm the submission
        System.out.println("Replenishment request submitted.");
    }
}
