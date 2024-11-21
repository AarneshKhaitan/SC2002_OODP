// controller/PatientAppointmentController.java
package controller.interfaces;

import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientAppointmentController extends AppointmentController {
    boolean scheduleAppointment(String patientId, String doctorId, LocalDateTime dateTime, String type);
    boolean cancelAppointment(String appointmentId);
    List<Appointment> getPatientAppointments(String patientId);
    boolean rescheduleAppointment(String appointmentId, LocalDateTime newDateTime);
    List<Appointment> getReschedulableAppointments(String patientId);
    List<AppointmentSlot> getAvailableSlotsForRescheduling(String appointmentId);
}