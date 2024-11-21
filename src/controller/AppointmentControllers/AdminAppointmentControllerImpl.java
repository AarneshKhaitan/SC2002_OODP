
// controller/impl/AdminAppointmentControllerImpl.java
package controller.AppointmentControllers;

import controller.interfaces.AdminAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdminAppointmentControllerImpl implements AdminAppointmentController {
    private static AdminAppointmentControllerImpl instance;
    private final AppointmentManager appointmentManager;

    private AdminAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    public static AdminAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new AdminAppointmentControllerImpl();
        }
        return instance;
    }

    @Override
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return appointmentManager.getAvailableSlots(doctorId);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentManager.getAllAppointments();
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointmentManager.getAppointmentsByStatus(status);
    }

    @Override
    public List<Appointment> getTodaysAppointments() {
        return appointmentManager.getAppointmentsByDate(LocalDateTime.now().toLocalDate());
    }

    @Override
    public Map<String, Integer> getAppointmentStatistics() {
        return appointmentManager.getAppointmentStatistics();
    }

    @Override
    public boolean updateAppointmentStatus(String appointmentId,
                                           Appointment.AppointmentStatus status) {
        return appointmentManager.updateAppointmentStatus(appointmentId, status);
    }

    @Override
    public Appointment getAppointment(String appointmentId) {
        return appointmentManager.getAppointment(appointmentId);
    }
}