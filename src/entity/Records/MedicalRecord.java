package entity.Records;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a medical record for a patient.
 * Contains personal information, medical history, and treatment details.
 */
public class MedicalRecord {
    private String patientId;             // Unique ID of the patient
    private String name;                  // Name of the patient
    private LocalDate dateOfBirth;        // Date of birth of the patient
    private String gender;                // Gender of the patient
    private String phoneNumber;           // Contact phone number of the patient
    private String emailAddress;          // Email address of the patient
    private String bloodType;             // Blood type of the patient
    private List<Diagnosis> diagnoses;    // List of diagnoses for the patient
    private List<Treatment> treatments;   // List of treatments for the patient

    
    /**
     * Constructs a new MedicalRecord instance.
     *
     * @param patientId    the unique ID of the patient
     * @param name         the name of the patient
     * @param dateOfBirth  the date of birth of the patient
     * @param gender       the gender of the patient
     * @param phoneNumber  the contact phone number of the patient
     * @param emailAddress the email address of the patient
     * @param bloodType    the blood type of the patient
     */

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
    
    /**
     * Gets the patient's unique ID.
     *
     * @return the patient ID
     */
    public String getPatientId() { return patientId; }
    
    /**
     * Gets the patient's name.
     *
     * @return the name of the patient
     */
    public String getName() { return name; }
        /**
     * Gets the patient's date of birth.
     *
     * @return the date of birth of the patient
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    
    /**
     * Gets the patient's gender.
     *
     * @return the gender of the patient
     */
    public String getGender() { return gender; }
    
        /**
     * Gets the patient's phone number.
     *
     * @return the contact phone number of the patient
     */
    public String getPhoneNumber() { return phoneNumber; }
    
        /**
     * Gets the patient's email address.
     *
     * @return the email address of the patient
     */
    public String getEmailAddress() { return emailAddress; }
    
        /**
     * Gets the patient's blood type.
     *
     * @return the blood type of the patient
     */
    public String getBloodType() { return bloodType; }

    
    /**
     * Gets the list of diagnoses associated with the patient.
     *
     * @return a copy of the list of diagnoses
     */
    public List<Diagnosis> getDiagnoses() { return new ArrayList<>(diagnoses); }

    
    /**
     * Gets the list of treatments associated with the patient.
     *
     * @return a copy of the list of treatments
     */
    public List<Treatment> getTreatments() { return new ArrayList<>(treatments); }

    // Setters for non-medical information only

        /**
     * Sets the patient's phone number.
     *
     * @param phoneNumber the new phone number to set
     */
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    
    /**
     * Sets the patient's email address.
     *
     * @param emailAddress the new email address to set
     */
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    // Methods for medical updates (doctor only)

    
    /**
     * Adds a new diagnosis to the patient's medical record.
     *
     * @param diagnosis the diagnosis to add
     */
    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
    }

        /**
     * Adds a new treatment to the patient's medical record.
     *
     * @param treatment the treatment to add
     */
    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }
}
