// entity/Prescription.java
package entity.Medications;

public class Prescription {
    private String medicationName;
    private PrescriptionStatus status;
    public static int quantity;

    public Prescription(String medicationName, int quantity) {
        this.medicationName = medicationName;
        this.status = PrescriptionStatus.PENDING;
        this.quantity = quantity;
    }

    // Getters
    public String getMedicationName() { return medicationName; }
    public int getQuantity() { return quantity; }
    public PrescriptionStatus getStatus() { return status; }

    // Setter for status only - medications shouldn't change once prescribed
    public void setStatus(PrescriptionStatus status) { this.status = status; }
}
