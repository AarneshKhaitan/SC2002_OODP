package entity;

import java.time.LocalDate;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private String bloodType;

    public Patient(String patientId, String name, LocalDate dateOfBirth,
                   String gender, String bloodType, String email) {
        super(patientId, name, gender);
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.role = UserRole.PATIENT;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getBloodType() { return bloodType; }
}