package entity.Records;

import java.time.LocalDate;

public class Diagnosis {
    private LocalDate date;
    private String doctorId;
    private String condition;
    private String notes;

    public Diagnosis(LocalDate date, String doctorId, String condition, String notes) {
        this.date = date;
        this.doctorId = doctorId;
        this.condition = condition;
        this.notes = notes;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public String getDoctorId() { return doctorId; }
    public String getCondition() { return condition; }
    public String getNotes() { return notes; }
}