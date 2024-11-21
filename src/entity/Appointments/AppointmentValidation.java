package entity.Appointments;

import java.time.LocalDateTime;

/**
 * Provides utility methods to validate appointment details.
 * Includes checks for valid appointment times, types, and durations.
 */
public class AppointmentValidation {

    /**
     * Validates if the given appointment time is in the future.
     * 
     * @param dateTime the date and time of the appointment to be validated
     * @return {@code true} if the appointment time is in the future, {@code false} otherwise
     */
    public static boolean isValidAppointmentTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return dateTime != null && dateTime.isAfter(now);
    }

    /**
     * Validates if the given appointment type is valid.
     * The type is considered valid if it matches one of the defined appointment types in {@link AppointmentType}.
     * 
     * @param type the type of the appointment to be validated
     * @return {@code true} if the appointment type is valid, {@code false} otherwise
     */
    public static boolean isValidAppointmentType(String type) {
        try {
            AppointmentType.fromString(type); // Try to match with a valid appointment type
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Validates if the duration between the start and end times of the appointment is valid.
     * Appointments must be between 30 minutes and 2 hours long.
     * 
     * @param startTime the start time of the appointment
     * @param endTime   the end time of the appointment
     * @return {@code true} if the duration is valid, {@code false} otherwise
     */
    public static boolean isValidDuration(LocalDateTime startTime, LocalDateTime endTime) {
        // Assuming appointments should be at least 30 minutes and not more than 2 hours
        if (startTime == null || endTime == null) return false;

        long minutes = java.time.Duration.between(startTime, endTime).toMinutes();
        return minutes >= 30 && minutes <= 120;
    }
}
