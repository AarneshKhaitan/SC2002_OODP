// controller/DoctorAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorAppointmentController extends AppointmentController {
    List<Appointment> getDoctorAppointments(String doctorId);
    List<Appointment> getAppointmentRequests(String doctorId);
    List<Appointment> getDoctorUpcomingAppointments(String doctorId);
    boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots);
    boolean handleAppointmentRequest(String appointmentId, boolean accept);
}