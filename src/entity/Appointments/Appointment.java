// entity/Appointment.java
package entity.Appointments;

import java.time.LocalDateTime;

/**
 * Represents an appointment within the system.
 * Includes details such as appointment ID, patient, doctor, date, time, and type.
 * Provides methods to manage and retrieve appointment information.
 */
public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String type;    // consultation, X-ray, blood test, etc.

    public enum AppointmentStatus {
        REQUESTED,      // Initial status when appointment is created
        CONFIRMED,      // After doctor accepts
        CANCELLED,      // If cancelled by either party
        COMPLETED      // After the appointment is done
    }

    public Appointment(String appointmentId, String patientId, String doctorId,
                       LocalDateTime dateTime, String type) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.REQUESTED;
        this.type = type;
    }

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AppointmentStatus getStatus() { return status; }
    public String getType() { return type; }

    // Setters
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", dateTime=" + dateTime +
                ", status=" + status +
                ", type='" + type + '\'' +
                '}';
    }
}
