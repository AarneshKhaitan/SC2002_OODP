package util;

import entity.Appointments.Appointment;

import java.util.List;

/**
 * Utility class for displaying appointment details.
 * Provides a method to display a list of appointments in a formatted manner.
 */
public class AppointmentDisplayUtil {

    // Private constructor to prevent instantiation
    private AppointmentDisplayUtil() {
    }

    /**
     * Displays a list of appointments with details such as appointment ID, doctor ID,
     * date & time, type, and status.
     * If the list of appointments is empty, a message is displayed indicating no appointments.
     *
     * @param appointments the list of appointments to be displayed
     */
    public static void displayAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments to display.");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.printf("""
                            ----------------------------------------
                            Appointment ID: %s
                            Doctor ID: %s
                            Date & Time: %s
                            Type: %s
                            Status: %s
                            """,
                    appointment.getAppointmentId(),
                    appointment.getDoctorId(),
                    UIUtils.formatDateTime(appointment.getDateTime()),
                    appointment.getType(),
                    appointment.getStatus());
        }
        System.out.println("----------------------------------------");
    }
}

