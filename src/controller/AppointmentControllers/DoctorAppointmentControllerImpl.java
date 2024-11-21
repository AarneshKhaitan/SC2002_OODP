// controller/impl/DoctorAppointmentControllerImpl.java
package controller.AppointmentControllers;

import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import util.AppointmentManager;
import java.time.LocalDateTime;
import java.util.List;

public class DoctorAppointmentControllerImpl implements DoctorAppointmentController {
    private static DoctorAppointmentControllerImpl instance;
    private final AppointmentManager appointmentManager;

    private DoctorAppointmentControllerImpl() {
        this.appointmentManager = AppointmentManager.getInstance();
    }

    public static DoctorAppointmentControllerImpl getInstance() {
        if (instance == null) {
            instance = new DoctorAppointmentControllerImpl();
        }
        return instance;
    }

    @Override
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return appointmentManager.getAvailableSlots(doctorId);
    }

    @Override
    public List<Appointment> getDoctorAppointments(String doctorId) {
        return appointmentManager.getAppointmentsForDoctor(doctorId);
    }

    @Override
    public List<Appointment> getAppointmentRequests(String doctorId) {
        return appointmentManager.getAppointmentsByDoctorAndStatus(
                doctorId, Appointment.AppointmentStatus.REQUESTED);
    }

    @Override
    public List<Appointment> getDoctorUpcomingAppointments(String doctorId) {
        return appointmentManager.getDoctorUpcomingAppointments(doctorId);
    }

    @Override
    public boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots) {
        return appointmentManager.addDoctorSlots(doctorId, slots);
    }

    @Override
    public boolean handleAppointmentRequest(String appointmentId, boolean accept) {
        Appointment.AppointmentStatus newStatus = accept ?
                Appointment.AppointmentStatus.CONFIRMED :
                Appointment.AppointmentStatus.CANCELLED;
        return appointmentManager.updateAppointmentStatus(appointmentId, newStatus);
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