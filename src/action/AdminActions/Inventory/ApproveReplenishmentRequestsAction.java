package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import entity.Medications.ReplenishmentRequest;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

/**
 * Handles the approval or rejection of replenishment requests for medications.
 * <p>
 * This action allows administrators to review pending replenishment requests,
 * approve or reject them, and update the medication inventory if the request
 * is approved.
 * </p>
 */
public class ApproveReplenishmentRequestsAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs an {@code ApproveReplenishmentRequestsAction} instance.
     *
     * @param inventoryManager the inventory manager used for managing replenishment requests and medication data
     */
    public ApproveReplenishmentRequestsAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to manage replenishment requests.
     * <p>
     * Displays a list of all pending replenishment requests, prompts the administrator to select a request,
     * and allows approval or rejection. If a request is approved, the medication's stock is updated.
     * </p>
     * <ul>
     *     <li>Displays all replenishment requests with details such as medication name, quantity, pharmacist ID, and status.</li>
     *     <li>Checks if a request has already been processed before allowing further action.</li>
     *     <li>Updates the medication stock and saves the inventory if the request is approved.</li>
     *     <li>Saves the updated status of the replenishment request.</li>
     * </ul>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        List<ReplenishmentRequest> requests = inventoryManager.getReplenishmentRequests();

        if (requests.isEmpty()) {
            System.out.println("No replenishment requests available.");
            return;
        }

        System.out.println("=== Replenishment Requests ===");
        int index = 0;
        for (ReplenishmentRequest request : requests) {
            System.out.printf("[%d] Medication: %s | Quantity: %d | Pharmacist: %s | Status: %s%n",
                    index++,
                    request.getMedicationName(),
                    request.getQuantity(),
                    request.getPharmacistId(),
                    request.getStatus());
        }

        System.out.print("Enter the index of the request to approve (or -1 to go back): ");
        int requestIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (requestIndex >= 0 && requestIndex < requests.size()) {
            ReplenishmentRequest request = requests.get(requestIndex);

            if (!"Pending".equalsIgnoreCase(request.getStatus())) {
                System.out.println("Request has already been processed.");
                return;
            }

            System.out.println("Do you want to approve this request? (yes/no): ");
            String decision = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(decision)) {
                request.approve();

                for (Medication medication : inventoryManager.getMedications()) {
                    if (medication.getName().equalsIgnoreCase(request.getMedicationName())) {
                        medication.increaseStock(request.getQuantity());
                        InventoryManager.saveMedications(inventoryManager.getMedications());
                        break;
                    }
                }

                System.out.println("Request approved and stock updated.");
            } else if ("no".equals(decision)) {
                request.reject();
                System.out.println("Request rejected.");
            } else {
                System.out.println("Invalid input. Returning to menu.");
            }

            InventoryManager.saveReplenishmentRequests(requests); // Save request updates
        } else if (requestIndex == -1) {
            System.out.println("Returning to menu...");
        } else {
            System.out.println("Invalid index. Returning to menu.");
        }
    }
}
