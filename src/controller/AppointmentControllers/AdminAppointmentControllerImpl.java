/**
 * Implementation of the {@link AdminAppointmentController} interface.
 * Provides administrative functionalities for managing appointments, including
 * retrieving, updating, and analyzing appointment data.
 */
package controller.AppointmentControllers;

import controller.interfaces.AdminAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The {@code AdminAppointmentControllerImpl} class provides concrete implementations
 * of methods for managing appointments. It interacts with the {@link AppointmentManager}
 * to perform operations such as fetching available slots, retrieving appointment statistics,
 * and updating appointment statuses.
 *
 * This class follows the Singleton design pattern, ensuring that only one instance exists.
 */
public class AdminAppointmentControllerImpl implements AdminAppointmentController {

    /** The single instance of this class (Singleton). */
    private static AdminAppointmentControllerImpl instance;

    /** The {@link AppointmentManager} instance to manage appointments. */
    private final AppointmentManager appointmentManager;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the {@code AppointmentManager} instance.
     */
    private AdminAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    /**
     * Returns the single instance of {@code AdminAppointmentControllerImpl}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static AdminAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new AdminAppointmentControllerImpl();
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
     * Retrieves all appointments in the system.
     *
     * @return a list of all appointments.
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentManager.getAllAppointments();
    }

    /**
     * Retrieves appointments filtered by their status.
     *
     * @param status the status to filter appointments by.
     * @return a list of appointments matching the specified status.
     */
    @Override
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentManager.getAppointmentsByStatus(status);
    }

    /**
     * Retrieves all appointments scheduled for today.
     *
     * @return a list of today's appointments.
     */
    @Override
    public List<Appointment> getTodaysAppointments() {
        return appointmentManager.getAppointmentsByDate(LocalDateTime.now().toLocalDate());
    }

    /**
     * Retrieves statistics about appointments in the system.
     * The statistics include counts of appointments categorized by their statuses.
     *
     * @return a map containing appointment statistics, where keys are status names
     *         and values are counts.
     */
    @Override
    public Map<String, Integer> getAppointmentStatistics() {
        return appointmentManager.getAppointmentStatistics();
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
