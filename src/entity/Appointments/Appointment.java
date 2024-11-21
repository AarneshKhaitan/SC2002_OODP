package entity.Appointments;

import java.time.LocalDateTime;

/**
 * Represents an appointment within the system.
 * Stores details such as the appointment ID, patient, doctor, date, time, and type.
 * Provides methods to manage and retrieve appointment information.
 */
public class Appointment {
    private String appointmentId;   // Unique identifier for the appointment
    private String patientId;       // ID of the patient associated with the appointment
    private String doctorId;        // ID of the doctor associated with the appointment
    private LocalDateTime dateTime; // Date and time of the appointment
    private AppointmentStatus status; // Current status of the appointment
    private String type;            // Type of appointment (e.g., consultation, X-ray, blood test)

    /**
     * Enum representing the possible statuses of an appointment.
     */
    public enum AppointmentStatus {
        REQUESTED,   // Appointment is created but not yet accepted by the doctor
        CONFIRMED,   // Doctor has accepted the appointment
        CANCELLED,   // Appointment was cancelled by either the patient or doctor
        COMPLETED    // Appointment has been finished
    }

    /**
     * Constructs a new Appointment instance.
     *
     * @param appointmentId the unique ID for the appointment
     * @param patientId     the ID of the patient associated with the appointment
     * @param doctorId      the ID of the doctor associated with the appointment
     * @param dateTime      the date and time of the appointment
     * @param type          the type of appointment (e.g., consultation, X-ray, blood test)
     */
    public Appointment(String appointmentId, String patientId, String doctorId,
                       LocalDateTime dateTime, String type) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.REQUESTED; // Default status is "REQUESTED"
        this.type = type;
    }

    /**
     * Gets the unique ID of the appointment.
     *
     * @return the appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the ID of the patient associated with the appointment.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Gets the ID of the doctor associated with the appointment.
     *
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return the appointment date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the current status of the appointment.
     *
     * @return the status of the appointment
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Gets the type of the appointment (e.g., consultation, X-ray, blood test).
     *
     * @return the type of the appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the date and time of the appointment.
     *
     * @param dateTime the new date and time for the appointment
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status the new status of the appointment
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Sets the type of the appointment (e.g., consultation, X-ray, blood test).
     *
     * @param type the new type of the appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a string representation of the Appointment object.
     * The string includes the appointment ID, patient ID, doctor ID, date/time, status, and type.
     *
     * @return a string representation of the appointment
     */
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

