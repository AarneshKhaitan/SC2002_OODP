// controller/PatientAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for managing appointment-related operations specific to patients.
 *
 * <p>
 * This interface extends {@link AppointmentController} and provides additional methods tailored to
 * patient needs, such as:
 * <ul>
 *   <li>Scheduling new appointments.</li>
 *   <li>Canceling existing appointments.</li>
 *   <li>Rescheduling appointments.</li>
 *   <li>Retrieving patient-specific appointment details.</li>
 * </ul>
 * </p>
 */
public interface PatientAppointmentController extends AppointmentController {

    /**
     * Schedules a new appointment for a patient with a specific doctor.
     *
     * @param patientId The unique identifier of the patient.
     * @param doctorId  The unique identifier of the doctor.
     * @param dateTime  The date and time for the appointment.
     * @param type      The type of appointment (e.g., Consultation, X-ray).
     * @return {@code true} if the appointment was successfully scheduled, {@code false} otherwise.
     */
    boolean scheduleAppointment(String patientId, String doctorId, LocalDateTime dateTime, String type);

    /**
     * Cancels an existing appointment for a patient.
     *
     * @param appointmentId The unique identifier of the appointment to be canceled.
     * @return {@code true} if the appointment was successfully canceled, {@code false} otherwise.
     */
    boolean cancelAppointment(String appointmentId);

    /**
     * Retrieves all appointments associated with a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of {@link Appointment} objects representing the patient's appointments.
     */
    List<Appointment> getPatientAppointments(String patientId);

    /**
     * Reschedules an existing appointment to a new date and time.
     *
     * @param appointmentId The unique identifier of the appointment to be rescheduled.
     * @param newDateTime   The new date and time for the appointment.
     * @return {@code true} if the appointment was successfully rescheduled, {@code false} otherwise.
     */
    boolean rescheduleAppointment(String appointmentId, LocalDateTime newDateTime);

    /**
     * Retrieves appointments that can be rescheduled for a specific patient.
     *
     * <p>
     * Only appointments that are not canceled or completed are considered reschedulable.
     * </p>
     *
     * @param patientId The unique identifier of the patient.
     * @return A list of {@link Appointment} objects that are eligible for rescheduling.
     */
    List<Appointment> getReschedulableAppointments(String patientId);

    /**
     * Retrieves available time slots for rescheduling a specific appointment.
     *
     * @param appointmentId The unique identifier of the appointment to be rescheduled.
     * @return A list of {@link AppointmentSlot} objects representing available slots for rescheduling.
     */
    List<AppointmentSlot> getAvailableSlotsForRescheduling(String appointmentId);
}
