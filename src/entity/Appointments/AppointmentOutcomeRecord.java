package entity.Appointments;

import entity.Medications.Prescription;
import entity.Medications.PrescriptionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the outcome of a medical appointment.
 * Stores information such as the appointment ID, date, type, consultation notes,
 * prescribed medications, and the associated doctor and patient.
 */
public class AppointmentOutcomeRecord {
    
    private final String appointmentId;        // Unique ID for the appointment
    private final LocalDateTime appointmentDate; // Date and time of the appointment
    private final String appointmentType;      // Type of appointment (e.g., consultation, X-ray, etc.)
    private final List<Prescription> prescriptions; // List of prescribed medications
    private final String consultationNotes;    // Notes from the doctor about the consultation
    private final String doctorId;             // ID of the doctor who conducted the appointment
    private final String patientId;            // ID of the patient associated with the appointment

    /**
     * Constructs a new AppointmentOutcomeRecord instance.
     *
     * @param appointmentId     the unique ID of the appointment
     * @param appointmentDate   the date and time of the appointment
     * @param appointmentType   the type of the appointment
     * @param consultationNotes notes from the consultation
     * @param doctorId          the ID of the doctor who conducted the appointment
     * @param patientId         the ID of the patient associated with the appointment
     */
    public AppointmentOutcomeRecord(String appointmentId, LocalDateTime appointmentDate,
                                    String appointmentType, String consultationNotes,
                                    String doctorId, String patientId) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentType = appointmentType;
        this.prescriptions = new ArrayList<>();
        this.consultationNotes = consultationNotes;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    /**
     * Gets the unique ID of the appointment outcome record.
     *
     * @return the appointment ID
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the date and time of the appointment outcome.
     *
     * @return the appointment date and time
     */
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Gets the type of the appointment.
     *
     * @return the appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Gets the list of prescribed medications for the appointment.
     *
     * @return a copy of the list of prescriptions
     */
    public List<Prescription> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    /**
     * Gets the consultation notes for the appointment.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Gets the ID of the doctor who conducted the appointment.
     *
     * @return the doctor's ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the ID of the patient associated with the appointment.
     *
     * @return the patient's ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Adds a prescription to the appointment outcome record.
     *
     * @param medicationName the name of the medication being prescribed
     * @param quantity       the quantity of the medication
     */
    public void addPrescription(String medicationName, int quantity) {
        prescriptions.add(new Prescription(medicationName, quantity));
    }

    /**
     * Updates the status of an existing prescription in the appointment outcome record.
     * 
     * @param medicationName the name of the medication whose status needs to be updated
     * @param newStatus      the new status for the medication prescription
     * @return {@code true} if the status was updated successfully, {@code false} if the prescription was not found
     */
    public boolean updatePrescriptionStatus(String medicationName, PrescriptionStatus newStatus) {
        return prescriptions.stream()
                .filter(p -> p.getMedicationName().equals(medicationName))
                .findFirst()
                .map(p -> {
                    p.setStatus(newStatus);
                    return true;
                })
                .orElse(false);
    }
}
