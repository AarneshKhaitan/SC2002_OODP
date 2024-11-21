/**
 * Implementation of the {@link PatientAppointmentController} interface.
 * Provides functionalities for patients to manage their appointments, including
 * scheduling, rescheduling, canceling, and viewing appointment details.
 */
package controller.AppointmentControllers;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@code PatientAppointmentControllerImpl} class provides concrete implementations
 * of methods for patients to manage their appointments. It interacts with the {@link AppointmentManager}
 * to perform operations such as scheduling appointments, canceling them, and checking available slots.
 *
 * This class follows the Singleton design pattern, ensuring only one instance exists.
 */
public class PatientAppointmentControllerImpl implements PatientAppointmentController {

    /** The single instance of this class (Singleton). */
    private static PatientAppointmentControllerImpl instance;

    /** The {@link AppointmentManager} instance to manage appointments. */
    private final AppointmentManager appointmentManager;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the {@code AppointmentManager} instance.
     */
    private PatientAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    /**
     * Returns the single instance of {@code PatientAppointmentControllerImpl}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static PatientAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new PatientAppointmentControllerImpl();
        }
        return instance;
    }

    /**
     * Retrieves available appointment slots for a specified doctor.
     *
     * @param doctorId the unique identifier of the doctor.
     * @return a list of available appointment slots for the doctor.
     */
    @Override
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return appointmentManager.getAvailableSlots(doctorId);
    }

    /**
     * Schedules a new appointment for the patient with a specified doctor.
     *
     * @param patientId the unique identifier of the patient.
     * @param doctorId the unique identifier of the doctor.
     * @param dateTime the date and time of the appointment.
     * @param type the type of appointment (e.g., consultation, follow-up).
     * @return {@code true} if the appointment was successfully scheduled, {@code false} otherwise.
     */
    @Override
    public boolean scheduleAppointment(String patientId, String doctorId,
                                       LocalDateTime dateTime, String type) {
        return appointmentManager.scheduleAppointment(patientId, doctorId, dateTime, type);
    }

    /**
     * Cancels an appointment based on its unique identifier.
     *
     * @param appointmentId the unique identifier of the appointment.
     * @return {@code true} if the appointment was successfully canceled, {@code false} otherwise.
     */
    @Override
    public boolean cancelAppointment(String appointmentId) {
        return appointmentManager.cancelAppointment(appointmentId);
    }

    /**
     * Retrieves all appointments associated with a specific patient.
     *
     * @param patientId the unique identifier of the patient.
     * @return a list of the patient's appointments.
     */
    @Override
    public List<Appointment> getPatientAppointments(String patientId) {
        return appointmentManager.getAppointmentsForPatient(patientId);
    }

    /**
     * Reschedules an appointment to a new date and time.
     *
     * @param appointmentId the unique identifier of the appointment to reschedule.
     * @param newDateTime the new date and time for the appointment.
     * @return {@code true} if the appointment was successfully rescheduled, {@code false} otherwise.
     */
    @Override
    public boolean rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) {
        return appointmentManager.rescheduleAppointment(appointmentId, newDateTime);
    }

    /**
     * Retrieves reschedulable appointments for a specific patient.
     *
     * @param patientId the unique identifier of the patient.
     * @return a list of appointments that can be rescheduled.
     */
    @Override
    public List<Appointment> getReschedulableAppointments(String patientId) {
        return appointmentManager.getReschedulableAppointments(patientId);
    }

    /**
     * Retrieves available appointment slots for rescheduling a specific appointment.
     *
     * @param appointmentId the unique identifier of the appointment to reschedule.
     * @return a list of available slots for rescheduling the appointment.
     */
    @Override
    public List<AppointmentSlot> getAvailableSlotsForRescheduling(String appointmentId) {
        return appointmentManager.getAvailableSlotsForRescheduling(appointmentId);
    }

    /**
     * Updates the status of a specific appointment.
     *
     * @param appointmentId the unique identifier of the appointment to update.
     * @param status the new status to set for the appointment.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    @Override
    public boolean updateAppointmentStatus(String appointmentId,
                                           Appointment.AppointmentStatus status) {
        return appointmentManager.updateAppointmentStatus(appointmentId, status);
    }

    /**
     * Retrieves details of a specific appointment by its ID.
     *
     * @param appointmentId the unique identifier of the appointment.
     * @return the appointment object corresponding to the specified ID.
     */
    @Override
    public Appointment getAppointment(String appointmentId) {
        return appointmentManager.getAppointment(appointmentId);
    }
}
