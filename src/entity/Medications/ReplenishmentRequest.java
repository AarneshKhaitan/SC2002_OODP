package entity.Medications;

import java.util.UUID;

/**
 * Represents a medication replenishment request.
 * Contains details about the medication, requested quantity, pharmacist handling the request, and its approval status.
 */
public class ReplenishmentRequest {

    private final String id;            // Unique ID for the replenishment request
    private final String medicationName; // Name of the medication being requested
    private final int quantity;         // Quantity of the medication requested
    private final String pharmacistId;  // ID of the pharmacist handling the request
    private String status;              // Status of the request: Pending, Approved, Rejected

    /**
     * Constructs a new ReplenishmentRequest instance.
     * The request is assigned a unique ID and initially marked as "Pending".
     *
     * @param medicationName the name of the medication being requested
     * @param quantity       the quantity of the medication being requested
     * @param pharmacistId   the ID of the pharmacist handling the request
     */
    public ReplenishmentRequest(String medicationName, int quantity, String pharmacistId) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID for each request
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.pharmacistId = pharmacistId;
        this.status = "Pending"; // Default status is Pending
    }

    /**
     * Gets the unique ID of the replenishment request.
     *
     * @return the ID of the replenishment request
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the medication for the replenishment request.
     *
     * @return the medication name
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Gets the quantity of the medication requested.
     *
     * @return the quantity of the requested medication
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the ID of the pharmacist handling the replenishment request.
     *
     * @return the pharmacist ID
     */
    public String getPharmacistId() {
        return pharmacistId;
    }

    /**
     * Gets the current status of the replenishment request.
     * Possible statuses are: "Pending", "Approved", "Rejected".
     *
     * @return the status of the replenishment request
     */
    public String getStatus() {
        return status;
    }

    /**
     * Approves the replenishment request and changes the status to "Approved".
     */
    public void approve() {
        this.status = "Approved";
    }

    /**
     * Rejects the replenishment request and changes the status to "Rejected".
     */
    public void reject() {
        this.status = "Rejected";
    }

    /**
     * Returns a string representation of the ReplenishmentRequest.
     * The string contains the request's ID, medication name, quantity, pharmacist ID, and status.
     *
     * @return a string representation of the replenishment request
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%d,%s,%s", id, medicationName, quantity, pharmacistId, status);
    }
}

