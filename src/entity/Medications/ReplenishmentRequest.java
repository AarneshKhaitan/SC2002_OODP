package entity.Medications;

import java.util.UUID;

public class ReplenishmentRequest {
    private final String id;
    private final String medicationName;
    private final int quantity;
    private final String pharmacistId;
    private String status; // Pending, Approved, Rejected

    public ReplenishmentRequest(String medicationName, int quantity, String pharmacistId) {
        this.id = UUID.randomUUID().toString();
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.pharmacistId = pharmacistId;
        this.status = "Pending";
    }

    public String getId() {
        return id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        this.status = "Approved";
    }

    public void reject() {
        this.status = "Rejected";
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%s", id, medicationName, quantity, pharmacistId, status);
    }
}
