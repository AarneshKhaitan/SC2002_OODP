// entity/Prescription.java
package entity.Medications;


/**
 * Represents a prescription for a medication.
 * Stores information about the medication's name, its prescribed quantity, and its status.
 */
public class Prescription {
    private String medicationName;      // The name of the prescribed medication
    private PrescriptionStatus status;  // The current status of the prescription
    public static int quantity;         // The prescribed quantity of the medication

        /**
     * Constructs a new Prescription instance.
     *
     * @param medicationName the name of the medication being prescribed
     * @param quantity       the quantity of the medication being prescribed
     */
    public Prescription(String medicationName, int quantity) {
        this.medicationName = medicationName;
        this.status = PrescriptionStatus.PENDING;
        this.quantity = quantity;
    }

    // Getters

    
    /**
     * Gets the name of the prescribed medication.
     *
     * @return the name of the medication
     */
    public String getMedicationName() { return medicationName; }

        /**
     * Gets the prescribed quantity of the medication.
     *
     * @return the quantity of the medication prescribed
     */
    public int getQuantity() { return quantity; }

        /**
     * Gets the current status of the prescription.
     *
     * @return the status of the prescription
     */
    public PrescriptionStatus getStatus() { return status; }

    // Setter for status only - medications shouldn't change once prescribed

    
    /**
     * Sets the status of the prescription.
     * The status can only be updated, not the medication once prescribed.
     *
     * @param status the new status of the prescription
     */
    public void setStatus(PrescriptionStatus status) { this.status = status; }
}
