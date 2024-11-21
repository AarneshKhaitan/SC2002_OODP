package entity.Records;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String bloodType;
    private List<Diagnosis> diagnoses;
    private List<Treatment> treatments;

    public MedicalRecord(String patientId, String name, LocalDate dateOfBirth,
                         String gender, String phoneNumber, String emailAddress,
                         String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
    }

    // Getters
    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getBloodType() { return bloodType; }
    public List<Diagnosis> getDiagnoses() { return new ArrayList<>(diagnoses); }
    public List<Treatment> getTreatments() { return new ArrayList<>(treatments); }

    // Setters for non-medical information only
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    // Methods for medical updates (doctor only)
    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }
}