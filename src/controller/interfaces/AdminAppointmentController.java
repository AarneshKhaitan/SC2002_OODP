// controller/AdminAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;

import java.util.List;
import java.util.Map;

public interface AdminAppointmentController extends AppointmentController {
    List<Appointment> getAllAppointments();
    List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status);
    List<Appointment> getTodaysAppointments();
    Map<String, Integer> getAppointmentStatistics();
}