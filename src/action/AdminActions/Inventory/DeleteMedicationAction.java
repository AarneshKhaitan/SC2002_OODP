package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;
import java.util.Scanner;

public class DeleteMedicationAction implements AdminAction {
    private final InventoryManager inventoryManager;

    public DeleteMedicationAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication Name to Delete: ");
        String medicationName = scanner.nextLine();

        List<Medication> medications = inventoryManager.getMedications();
        for (Medication medication : medications) {
            if (medication.getName().equalsIgnoreCase(medicationName)) {
                medications.remove(medication);
                InventoryManager.saveMedications(medications);
                System.out.println("Medication deleted successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }
}
