package entity;

public class Medication {
    private String name;
    private int currentStock;
    private int lowStockAlert;

    public Medication(String name, int currentStock, int lowStockAlert) {
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockAlert = lowStockAlert;
    }

    public String getName() {
        return name;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void increaseStock(int quantity) {
        this.currentStock += quantity;
    }

    public void decreaseStock(int quantity) {
        this.currentStock = Math.max(0, this.currentStock - quantity);
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%d", name, currentStock, lowStockAlert);
    }
}
