package entity.Records;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents a medical treatment record for a patient.
 * Stores details about the treatment date, prescribing doctor, type of treatment, medications, and instructions.
 */
public class Treatment {

    private LocalDate date;              // The date the treatment was prescribed
    private String doctorId;            // The ID of the doctor who prescribed the treatment
    private String treatmentType;       // The type of treatment (e.g., physical therapy, medication)
    private List<String> medications;   // List of medications prescribed during the treatment
    private String instructions;        // Additional instructions for the treatment

        /**
     * Constructs a new Treatment instance.
     *
     * @param date          the date the treatment was prescribed
     * @param doctorId      the ID of the prescribing doctor
     * @param treatmentType the type of treatment
     * @param medications   the list of medications prescribed
     * @param instructions  additional instructions for the treatment
     */
    public Treatment(LocalDate date, String doctorId, String treatmentType,
                     List<String> medications, String instructions) {
        this.date = date;
        this.doctorId = doctorId;
        this.treatmentType = treatmentType;
        this.medications = new ArrayList<>(medications);
        this.instructions = instructions;
    }

    // Getters

    
    /**
     * Gets the date the treatment was prescribed.
     *
     * @return the date of the treatment
     */
    public LocalDate getDate() { return date; }

        /**
     * Gets the ID of the doctor who prescribed the treatment.
     *
     * @return the doctor's ID
     */
    public String getDoctorId() { return doctorId; }

    
    /**
     * Gets the type of treatment.
     *
     * @return the type of treatment
     */
    public String getTreatmentType() { return treatmentType; }

    
    /**
     * Gets the list of medications prescribed during the treatment.
     *
     * @return a copy of the list of medications
     */
    public List<String> getMedications() { return new ArrayList<>(medications); }

        /**
     * Gets additional instructions for the treatment.
     *
     * @return the treatment instructions
     */
    public String getInstructions() { return instructions; }
}
