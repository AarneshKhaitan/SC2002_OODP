/**
 * Controls operations related to managing medical records for patients.
 * Provides methods for both patients and doctors to interact with medical records,
 * including retrieving records, updating contact information, and adding diagnoses or treatments.
 * Utilizes {@link MedicalRecordManager} for the underlying data management.
 */
package controller.MedicalRecords;

import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import util.MedicalRecordManager;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code MedicalRecordController} class provides functionalities for managing medical records.
 * It serves as a centralized controller for operations related to retrieving, updating, and
 * modifying medical records. This class supports operations for both patients and doctors.
 *
 * This class follows the Singleton design pattern to ensure only one instance exists.
 */
public class MedicalRecordController {

    /** The single instance of this class (Singleton). */
    private static MedicalRecordController instance;

    /** The {@link MedicalRecordManager} instance for managing medical records. */
    private final MedicalRecordManager medicalRecordManager;

    /**
     * Private constructor to initialize the {@code MedicalRecordManager} instance.
     */
    private MedicalRecordController() {
        this.medicalRecordManager = MedicalRecordManager.getInstance();
    }

    /**
     * Returns the single instance of {@code MedicalRecordController}.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of this class.
     */
    public static MedicalRecordController getInstance() {
        if (instance == null) {
            instance = new MedicalRecordController();
        }
        return instance;
    }

    // Patient methods

    /**
     * Retrieves the medical record of a specific patient.
     *
     * @param patientId the unique identifier of the patient.
     * @return the {@link MedicalRecord} of the specified patient, or {@code null} if not found.
     */
    public MedicalRecord getPatientMedicalRecord(String patientId) {
        return medicalRecordManager.getMedicalRecord(patientId);
    }

    /**
     * Updates the contact information of a specific patient.
     *
     * @param patientId the unique identifier of the patient.
     * @param phoneNumber the updated phone number of the patient.
     * @param email the updated email address of the patient.
     * @return {@code true} if the contact information was successfully updated, {@code false} otherwise.
     */
    public boolean updateContactInfo(String patientId, String phoneNumber, String email) {
        return medicalRecordManager.updateContactInfo(patientId, phoneNumber, email);
    }

    // Doctor methods

    /**
     * Adds a diagnosis to a patient's medical record.
     *
     * @param patientId the unique identifier of the patient.
     * @param doctorId the unique identifier of the doctor providing the diagnosis.
     * @param condition the condition diagnosed.
     * @param notes additional notes or observations related to the diagnosis.
     * @return {@code true} if the diagnosis was successfully added, {@code false} otherwise.
     */
    public boolean addDiagnosis(String patientId, String doctorId,
                                String condition, String notes) {
        Diagnosis diagnosis = new Diagnosis(
                LocalDate.now(),
                doctorId,
                condition,
                notes
        );
        return medicalRecordManager.addDiagnosis(patientId, diagnosis);
    }

    /**
     * Adds a treatment plan to a patient's medical record.
     *
     * @param patientId the unique identifier of the patient.
     * @param doctorId the unique identifier of the doctor providing the treatment.
     * @param treatmentType the type of treatment (e.g., surgery, therapy, medication).
     * @param medications a list of medications prescribed for the treatment.
     * @param instructions additional instructions for the treatment.
     * @return {@code true} if the treatment was successfully added, {@code false} otherwise.
     */
    public boolean addTreatment(String patientId, String doctorId,
                                String treatmentType, List<String> medications,
                                String instructions) {
        Treatment treatment = new Treatment(
                LocalDate.now(),
                doctorId,
                treatmentType,
                medications,
                instructions
        );
        return medicalRecordManager.addTreatment(patientId, treatment);
    }
}
