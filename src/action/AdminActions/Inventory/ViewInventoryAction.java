package action.AdminActions.Inventory;

import action.AdminActions.AdminAction;
import entity.Medications.Medication;
import util.InventoryManager;

import java.util.List;

public class ViewInventoryAction implements AdminAction {
    private final InventoryManager inventoryManager;

    public ViewInventoryAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute() {
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
}
