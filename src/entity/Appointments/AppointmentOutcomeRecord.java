// entity/AppointmentOutcomeRecord.java
package entity.Appointments;

import entity.Medications.Prescription;
import entity.Medications.PrescriptionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcomeRecord {
    private final String appointmentId;
    private final LocalDateTime appointmentDate;
    private final String appointmentType;
    private final List<Prescription> prescriptions;
    private final String consultationNotes;
    private final String doctorId;
    private final String patientId;

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

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public String getAppointmentType() { return appointmentType; }
    public List<Prescription> getPrescriptions() { return new ArrayList<>(prescriptions); }
    public String getConsultationNotes() { return consultationNotes; }
    public String getDoctorId() { return doctorId; }
    public String getPatientId() { return patientId; }

    // Methods to manage prescriptions
    public void addPrescription(String medicationName, int quantity) {
        prescriptions.add(new Prescription(medicationName, quantity));
    }

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