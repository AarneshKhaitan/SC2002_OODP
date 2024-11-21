// entity/AppointmentValidation.java
package entity.Appointments;

import java.time.LocalDateTime;

public class AppointmentValidation {
    public static boolean isValidAppointmentTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return dateTime != null && dateTime.isAfter(now);
    }

    public static boolean isValidAppointmentType(String type) {
        try {
            AppointmentType.fromString(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidDuration(LocalDateTime startTime, LocalDateTime endTime) {
        // Assuming appointments should be at least 30 minutes and not more than 2 hours
        if (startTime == null || endTime == null) return false;

        long minutes = java.time.Duration.between(startTime, endTime).toMinutes();
        return minutes >= 30 && minutes <= 120;
    }
}