// entity/PrescriptionStatus.java
package entity.Medications;
/**
 * Represents the status of a prescription.
 * Provides predefined statuses such as Pending, Dispensed, and Cancelled.
 */
public enum PrescriptionStatus {
    PENDING("Pending"),     // Prescription is pending approval or processing
    DISPENSED("Dispensed"), // Prescription has been dispensed
    CANCELLED("Cancelled"); // Prescription has been cancelled

    private final String displayName; // The string representation of the status

    
    /**
     * Constructs a PrescriptionStatus with the specified display name.
     *
     * @param displayName the string representation of the status
     */
    PrescriptionStatus(String displayName) {
        this.displayName = displayName;
    }

        /**
     * Gets the string representation of the prescription status.
     *
     * @return the display name of the prescription status
     */
    public String getDisplayName() {
        return displayName;
    }
}
