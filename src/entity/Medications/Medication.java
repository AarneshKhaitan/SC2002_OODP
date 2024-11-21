package entity.Medications;

/**
 * Represents a medication item in the inventory.
 * Stores information about the medication's name, current stock, and low stock alert threshold.
 */
public class Medication {
    private String name;            // The name of the medication
    private int currentStock;       // The current stock quantity of the medication
    private int lowStockAlert;      // The threshold quantity that triggers a low stock alert

    
    /**
     * Constructs a new Medication instance.
     *
     * @param name           the name of the medication
     * @param currentStock   the current stock quantity of the medication
     * @param lowStockAlert  the threshold quantity for the low stock alert
     */

    public Medication(String name, int currentStock, int lowStockAlert) {
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
    }

    
    /**
     * Gets the name of the medication.
     *
     * @return the name of the medication
     */
    public String getName() {
        return name;
    }

        /**
     * Gets the current stock quantity of the medication.
     *
     * @return the current stock of the medication
     */
    public int getCurrentStock() {
        return currentStock;
    }

        /**
     * Gets the low stock alert threshold for the medication.
     *
     * @return the low stock alert threshold
     */
    public int getLowStockAlert() {
        return lowStockAlert;
    }

    
    /**
     * Increases the stock quantity of the medication by the specified amount.
     *
     * @param quantity the quantity to add to the current stock
     */
    public void increaseStock(int quantity) {
        this.currentStock += quantity;
    }

    
    /**
     * Decreases the stock quantity of the medication by the specified amount.
     * Ensures that the stock does not go below zero.
     *
     * @param quantity the quantity to subtract from the current stock
     */
    public void decreaseStock(int quantity) {
        this.currentStock = Math.max(0, this.currentStock - quantity);
    }

        /**
     * Sets the threshold quantity for the low stock alert.
     *
     * @param lowStockAlert the new threshold for the low stock alert
     */
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

        /**
     * Returns a string representation of the Medication object.
     * The string contains the medication's name, current stock, and low stock alert.
     *
     * @return a string representation of the medication
     */
    @Override
    public String toString() {
        return String.format("%s,%d,%d", name, currentStock, lowStockAlert);
    }
}
