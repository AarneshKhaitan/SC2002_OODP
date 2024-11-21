// controller/AppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;

import java.util.List;

public interface AppointmentController {
    List<AppointmentSlot> getAvailableSlots(String doctorId);
    boolean updateAppointmentStatus(String appointmentId, Appointment.AppointmentStatus status);
    Appointment getAppointment(String appointmentId);
}