package action;

import entity.Medication;
import entity.ReplenishmentRequest;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

public class AdminAction {
    private final InventoryManager inventoryManager;

    public AdminAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    // View all medications in the inventory
    public void viewInventory() {
        System.out.println("=== Medication Inventory ===");
        List<Medication> medications = inventoryManager.getMedications();
        if (medications.isEmpty()) {
            System.out.println("No medications in inventory.");
            return;
        }
        for (Medication medication : medications) {
            System.out.printf("Name: %s | Stock: %d | Low Stock Alert: %d%n",
                    medication.getName(),
                    medication.getCurrentStock(),
                    medication.getLowStockAlert());
        }
    }

    // Update the stock level of a specific medication
    public void updateStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name: ");
        String medicationName = scanner.nextLine();
        System.out.print("Enter New Stock Level: ");
        int newStock = scanner.nextInt();

        List<Medication> medications = inventoryManager.getMedications();
        for (Medication medication : medications) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                medication.increaseStock(newStock - medication.getCurrentStock());
                InventoryManager.saveMedications(medications); // Save updated inventory
                System.out.println("Stock updated successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }

    // Approve or reject replenishment requests
    public void approveReplenishmentRequests() {
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
