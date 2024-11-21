// entity/AppointmentType.java
package entity.Appointments;

public enum AppointmentType {
    CONSULTATION("Consultation"),
    XRAY("X-ray"),
    BLOOD_TEST("Blood Test");

    private final String displayName;

    AppointmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static AppointmentType fromString(String text) {
        for (AppointmentType type : AppointmentType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No appointment type found for: " + text);
    }
}