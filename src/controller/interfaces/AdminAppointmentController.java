// controller/AdminAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;

import java.util.List;
import java.util.Map;

/**
 * Interface for managing appointment-related operations for administrators.
 *
 * <p>
 * This interface extends {@link AppointmentController} and provides additional
 * methods specific to administrative tasks, such as:
 * <ul>
 *   <li>Viewing all appointments.</li>
 *   <li>Filtering appointments by status.</li>
 *   <li>Retrieving today's appointments.</li>
 *   <li>Generating appointment statistics.</li>
 * </ul>
 * </p>
 */
public interface AdminAppointmentController extends AppointmentController {
    /**
     * Retrieves all appointments in the system.
     *
     * @return A list of all {@link Appointment} objects.
     */
    List<Appointment> getAllAppointments();
    /**
     * Retrieves appointments filtered by a specific status.
     *
     * @param status The {@link Appointment.AppointmentStatus} to filter by.
     * @return A list of {@link Appointment} objects matching the specified status.
     */
    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);
    /**
     * Retrieves all appointments scheduled for today.
     *
     * @return A list of {@link Appointment} objects for today's date.
     */
    List<Appointment> getTodaysAppointments();
    /**
     * Generates statistics about appointments in the system.
     *
     * <p>
     * The statistics may include:
     * <ul>
     *   <li>Total number of appointments.</li>
     *   <li>Counts of appointments by status.</li>
     *   <li>Other relevant metrics for administrative reporting.</li>
     * </ul>
     * </p>
     *
     * @return A {@link Map} where the key is a string representing the statistic
     *         (e.g., "Completed", "Cancelled") and the value is the count of appointments.
     */
    Map<String, Integer> getAppointmentStatistics();
}
