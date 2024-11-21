/**
 * Implementation of the {@link DoctorAppointmentController} interface.
 * Provides functionalities for doctors to manage their appointments, including
 * viewing, handling requests, adding slots, and updating appointment statuses.
 */
package controller.AppointmentControllers;

import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@code DoctorAppointmentControllerImpl} class provides concrete implementations
 * of methods for doctors to manage appointments. It interacts with the {@link AppointmentManager}
 * to perform operations such as retrieving appointments, handling appointment requests,
 * and adding available slots.
 *
 * This class follows the Singleton design pattern, ensuring only one instance exists.
 */
public class DoctorAppointmentControllerImpl implements DoctorAppointmentController {

    /** The single instance of this class (Singleton). */
    private static DoctorAppointmentControllerImpl instance;

    /** The {@link AppointmentManager} instance to manage appointments. */
    private final AppointmentManager appointmentManager;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the {@code AppointmentManager} instance.
     */
    private DoctorAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    /**
     * Returns the single instance of {@code DoctorAppointmentControllerImpl}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static DoctorAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new DoctorAppointmentControllerImpl();
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
     * Retrieves all appointments associated with a specific doctor.
     *
     * @param doctorId the unique identifier of the doctor.
     * @return a list of the doctor's appointments.
     */
    @Override
    public List<Appointment> getDoctorAppointments(String doctorId) {
        return appointmentManager.getAppointmentsForDoctor(doctorId);
    }

    /**
     * Retrieves all appointment requests for a specific doctor.
     * Appointment requests are those with a status of {@code REQUESTED}.
     *
     * @param doctorId the unique identifier of the doctor.
     * @return a list of appointment requests for the doctor.
     */
    @Override
    public List<Appointment> getAppointmentRequests(String doctorId) {
        return appointmentManager.getAppointmentsByDoctorAndStatus(
                doctorId, Appointment.AppointmentStatus.REQUESTED);
    }

    /**
     * Retrieves upcoming appointments for a specific doctor.
     *
     * @param doctorId the unique identifier of the doctor.
     * @return a list of the doctor's upcoming appointments.
     */
    @Override
    public List<Appointment> getDoctorUpcomingAppointments(String doctorId) {
        return appointmentManager.getDoctorUpcomingAppointments(doctorId);
    }

    /**
     * Adds new appointment slots for a specific doctor.
     *
     * @param doctorId the unique identifier of the doctor.
     * @param slots a list of {@link LocalDateTime} objects representing the new slots to add.
     * @return {@code true} if the slots were successfully added, {@code false} otherwise.
     */
    @Override
    public boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots) {
        return appointmentManager.addDoctorSlots(doctorId, slots);
    }

    /**
     * Handles an appointment request by either accepting or rejecting it.
     *
     * @param appointmentId the unique identifier of the appointment.
     * @param accept {@code true} to accept the request, {@code false} to reject it.
     * @return {@code true} if the request was successfully handled, {@code false} otherwise.
     */
    @Override
    public boolean handleAppointmentRequest(String appointmentId, boolean accept) {
        Appointment.AppointmentStatus newStatus = accept ?
                Appointment.AppointmentStatus.CONFIRMED :
                Appointment.AppointmentStatus.CANCELLED;
        return appointmentManager.updateAppointmentStatus(appointmentId, newStatus);
    }

    /**
     * Updates the status of a specific appointment.
     *
     * @param appointmentId the unique identifier of the appointment to update.
     * @param status the new status to set for the appointment.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     */
    @Override
    public boolean updateAppointmentStatus(String appointmentId, Appointment.AppointmentStatus status) {
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
