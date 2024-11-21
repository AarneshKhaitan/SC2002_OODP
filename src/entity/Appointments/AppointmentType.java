package entity.Appointments;

/**
 * Enum representing the types of appointments available in the system.
 * Provides predefined types such as Consultation, X-ray, and Blood Test.
 */
public enum AppointmentType {

    CONSULTATION("Consultation"),  // Represents a general consultation appointment
    XRAY("X-ray"),                // Represents an X-ray appointment
    BLOOD_TEST("Blood Test");      // Represents a blood test appointment

    private final String displayName; // The string representation of the appointment type

    /**
     * Constructs an AppointmentType with the specified display name.
     *
     * @param displayName the string representation of the appointment type
     */
    AppointmentType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the string representation of the appointment type.
     *
     * @return the display name of the appointment type
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the string representation of the appointment type.
     *
     * @return the display name of the appointment type
     */
    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Converts a string to its corresponding AppointmentType.
     * 
     * @param text the string to convert
     * @return the AppointmentType corresponding to the string
     * @throws IllegalArgumentException if no matching AppointmentType is found
     */
    public static AppointmentType fromString(String text) {
        for (AppointmentType type : AppointmentType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No appointment type found for: " + text);
    }
}
