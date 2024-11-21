package entity.Records;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Treatment {
    private LocalDate date;
    private String doctorId;
    private String treatmentType;
    private List<String> medications;
    private String instructions;

    public Treatment(LocalDate date, String doctorId, String treatmentType,
                     List<String> medications, String instructions) {
        this.date = date;
        this.doctorId = doctorId;
        this.treatmentType = treatmentType;
        this.medications = new ArrayList<>(medications);
        this.instructions = instructions;
    }

    // Getters
    public LocalDate getDate() { return date; }
    public String getDoctorId() { return doctorId; }
    public String getTreatmentType() { return treatmentType; }
    public List<String> getMedications() { return new ArrayList<>(medications); }
    public String getInstructions() { return instructions; }
}