// entity/PrescriptionStatus.java
package entity.Medications;

public enum PrescriptionStatus {
    PENDING("Pending"),
    DISPENSED("Dispensed"),
    CANCELLED("Cancelled");

    private final String displayName;

    PrescriptionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}