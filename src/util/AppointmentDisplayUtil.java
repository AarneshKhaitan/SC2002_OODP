package util;

import entity.Appointments.Appointment;

import java.util.List;

public class AppointmentDisplayUtil {
    private AppointmentDisplayUtil() {
        // Private constructor to prevent instantiation
    }

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
