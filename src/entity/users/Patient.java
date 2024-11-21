package entity.users;

import java.time.LocalDate;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private String bloodType;
    private String email;

    public Patient(String patientId, String name, LocalDate dateOfBirth,
                   String gender, String bloodType, String email) {
        super(patientId, name, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.role = UserRole.PATIENT;
        this.email = email;
    }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getBloodType() { return bloodType; }
}