// controller/DoctorAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for managing appointment-related operations specific to doctors.
 *
 * This interface extends {@link AppointmentController} and provides additional methods tailored to
 * doctors' needs, such as:
 * <ul>
 *   <li>Retrieving a doctor's appointments.</li>
 *   <li>Handling appointment requests.</li>
 *   <li>Adding available time slots for appointments.</li>
 *   <li>Retrieving upcoming appointments for a doctor.</li>
 * </ul>
 */
public interface DoctorAppointmentController extends AppointmentController {

    /**
     * Retrieves all appointments associated with a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of {@link Appointment} objects representing the doctor's appointments.
     */
    List<Appointment> getDoctorAppointments(String doctorId);

    /**
     * Retrieves appointment requests for a specific doctor.
     *
     * Appointment requests are those awaiting the doctor's approval or response.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of {@link Appointment} objects representing the appointment requests.
     */
    List<Appointment> getAppointmentRequests(String doctorId);

    /**
     * Retrieves upcoming appointments for a specific doctor.
     *
     * Upcoming appointments are those scheduled in the future that have not been canceled or completed.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of {@link Appointment} objects representing the upcoming appointments.
     */
    List<Appointment> getDoctorUpcomingAppointments(String doctorId);

    /**
     * Adds new time slots to a doctor's availability for appointments.
     *
     * @param doctorId The unique identifier of the doctor.
     * @param slots    A list of {@link LocalDateTime} objects representing the new available time slots.
     * @return {@code true} if the slots were successfully added, {@code false} otherwise.
     */
    boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots);

    /**
     * Handles an appointment request by accepting or declining it.
     *
     * @param appointmentId The unique identifier of the appointment to be handled.
     * @param accept         {@code true} to accept the request, {@code false} to decline it.
     * @return {@code true} if the request was successfully handled, {@code false} otherwise.
     */
    boolean handleAppointmentRequest(String appointmentId, boolean accept);
}
