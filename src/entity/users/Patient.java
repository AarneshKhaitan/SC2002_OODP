/**
 * Represents a Patient user in the system.
 * Inherits from the {@link User} class and includes additional attributes specific to patients,
 * such as date of birth, blood type, and email.
 * The {@link UserRole#PATIENT} role is automatically assigned.
 * 
 * @author [Your Name]
 * @version 1.0
 */package entity.users;

import java.time.LocalDate;

public class Patient extends User {
    /**
     * The date of birth of the patient.
      * The blood type of the patient.
      * The email address of the patient.
     */
    private LocalDate dateOfBirth;
    private String bloodType;
    private String email;
     /**
     *  Constructs a Patient object with the specified attributes.
     * @param patientId   the unique identifier for the patient
     * @param name        the name of the patient
     * @param dateOfBirth the date of birth of the patient
     * @param gender      the gender of the patient
     * @param bloodType   the blood type of the patient
     * @param email       the email address of the patient
    */
  
    public Patient(String patientId, String name, LocalDate dateOfBirth,
                   String gender, String bloodType, String email) {
        super(patientId, name, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.role = UserRole.PATIENT;
        this.email = email;
    }
    /**
     * Retrieves the date of birth of the patient.
     * 
     * @return the date of birth of the patient
     * Retrieves the blood type of the patient.
     * 
     * @return the blood type of the patient
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getBloodType() { return bloodType; }
}
