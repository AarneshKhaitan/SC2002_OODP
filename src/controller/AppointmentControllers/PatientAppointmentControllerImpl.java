// controller/impl/PatientAppointmentControllerImpl.java
package controller.AppointmentControllers;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;

public class PatientAppointmentControllerImpl implements PatientAppointmentController {
    private static PatientAppointmentControllerImpl instance;
    private final AppointmentManager appointmentManager;

    private PatientAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    public static PatientAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new PatientAppointmentControllerImpl();
        }
        return instance;
    }

    @Override
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return appointmentManager.getAvailableSlots(doctorId);
    }

    @Override
    public boolean scheduleAppointment(String patientId, String doctorId,
                                       LocalDateTime dateTime, String type) {
        return appointmentManager.scheduleAppointment(patientId, doctorId, dateTime, type);
    }

    @Override
    public boolean cancelAppointment(String appointmentId) {
        return appointmentManager.cancelAppointment(appointmentId);
    }

    @Override
    public List<Appointment> getPatientAppointments(String patientId) {
        return appointmentManager.getAppointmentsForPatient(patientId);
    }

    @Override
    public boolean rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) {
        return appointmentManager.rescheduleAppointment(appointmentId, newDateTime);
    }

    @Override
    public List<Appointment> getReschedulableAppointments(String patientId) {
        return appointmentManager.getReschedulableAppointments(patientId);
    }

    @Override
    public List<AppointmentSlot> getAvailableSlotsForRescheduling(String appointmentId) {
        return appointmentManager.getAvailableSlotsForRescheduling(appointmentId);
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