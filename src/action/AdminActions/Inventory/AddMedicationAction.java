package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.Scanner;

/**
 * Handles the addition of new medications to the inventory.
 * <p>
 * This action allows administrators to add a new medication by specifying its name,
 * initial stock level, and low stock alert level. It ensures that duplicate medications
 * are not added.
 * </p>
 */
public class AddMedicationAction implements AdminAction {
    private final InventoryManager inventoryManager;

    /**
     * Constructs an {@code AddMedicationAction} instance.
     *
     * @param inventoryManager the inventory manager used for managing medication data
     */
    public AddMedicationAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Executes the action to add a new medication to the inventory.
     * <p>
     * Prompts the administrator to enter the medication's name, initial stock level,
     * and low stock alert level. Checks for duplicates in the inventory before adding
     * the new medication. Saves the updated inventory after the addition.
     * </p>
     */
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
