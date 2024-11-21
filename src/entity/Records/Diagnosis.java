package entity.Records;

import java.time.LocalDate;
/**
 * Represents a medical diagnosis record.
 * Stores details such as the diagnosis date, doctor ID, condition, and additional notes.
 */
public class Diagnosis {
   private LocalDate date;      // The date of the diagnosis
   private String doctorId;    // The ID of the doctor who made the diagnosis
   private String condition;   // The diagnosed condition
   private String notes;       // Additional notes related to the diagnosis

        /**
     * Constructs a new Diagnosis instance.
     *
     * @param date the date of the diagnosis
     * @param doctorId the ID of the doctor who made the diagnosis
     * @param condition the diagnosed medical condition
     * @param notes additional notes about the diagnosis
     */ 
    
    public Diagnosis(LocalDate date, String doctorId, String condition, String notes) {
        this.date = date;
        this.doctorId = doctorId;
        this.condition = condition;
        this.notes = notes;
    }

    // Getters
        /**
     * Gets the date of the diagnosis.
     *
     * @return the diagnosis date
     */
    public LocalDate getDate() { return date; }
    
    /**
     * Gets the ID of the doctor who made the diagnosis.
     *
     * @return the doctor's ID
     */
    public String getDoctorId() { return doctorId; }
    
    /**
     * Gets the diagnosed medical condition.
     *
     * @return the diagnosed condition
     */
    public String getCondition() { return condition; }
    
    /**
     * Gets additional notes about the diagnosis.
     *
     * @return the notes related to the diagnosis
     */
    public String getNotes() { return notes; }
}
