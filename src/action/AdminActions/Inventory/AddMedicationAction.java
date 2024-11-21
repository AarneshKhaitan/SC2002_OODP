package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.Scanner;

public class AddMedicationAction implements AdminAction {
    private final InventoryManager inventoryManager;

    public AddMedicationAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter New Medication Name: ");
        String medicationName = scanner.nextLine();

        for (Medication medication : inventoryManager.getMedications()) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                System.out.println("Medication already exists in the inventory.");
                return;
            }
        }

        System.out.print("Enter Initial Stock Level: ");
        int initialStock = scanner.nextInt();

        System.out.print("Enter Low Stock Alert Level: ");
        int lowStockAlert = scanner.nextInt();

        Medication newMedication = new Medication(medicationName, initialStock, lowStockAlert);
        inventoryManager.getMedications().add(newMedication);

        InventoryManager.saveMedications(inventoryManager.getMedications());

        System.out.println("New medication added successfully.");
    }
}
