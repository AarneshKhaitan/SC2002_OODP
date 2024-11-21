// controller/AppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;

import java.util.List;

/**
 * Interface for managing generic appointment-related operations.
 *
 * <p>
 * This interface defines common methods for handling appointments, including:
 * <ul>
 *   <li>Retrieving available slots for a doctor.</li>
 *   <li>Updating the status of an appointment.</li>
 *   <li>Fetching details of a specific appointment.</li>
 * </ul>
 * </p>
 */
public interface AppointmentController {

    /**
     * Retrieves a list of available appointment slots for a specified doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of {@link AppointmentSlot} objects representing the available slots.
     */
    List<AppointmentSlot> getAvailableSlots(String doctorId);

    /**
     * Updates the status of a specific appointment.
     *
     * @param appointmentId The unique identifier of the appointment to be updated.
     * @param status         The new {@link Appointment.AppointmentStatus} to set for the appointment.
     * @return {@code true} if the status was successfully updated, {@code false} otherwise.
     */
    boolean updateAppointmentStatus(String appointmentId, Appointment.AppointmentStatus status);

    /**
     * Retrieves the details of a specific appointment.
     *
     * @param appointmentId The unique identifier of the appointment to be retrieved.
     * @return The {@link Appointment} object representing the appointment, or {@code null} if not found.
     */
    Appointment getAppointment(String appointmentId);
}
