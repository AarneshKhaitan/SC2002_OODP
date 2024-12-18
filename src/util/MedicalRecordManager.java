package util;

import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages the creation, retrieval, and persistence of medical records, diagnoses, and treatments.
 * 
 * This singleton class provides centralized management for:
 * <ul>
 *   <li>Loading and saving medical record data, including diagnoses and treatments.</li>
 *   <li>Retrieving and updating patient information.</li>
 *   <li>Adding new diagnoses and treatments to medical records.</li>
 * </ul>
 *
 * The data is stored in three CSV files:
 * <ul>
 *   <li>{@code MedicalRecords.csv} for basic patient information.</li>
 *   <li>{@code Diagnoses.csv} for patient diagnoses.</li>
 *   <li>{@code Treatments.csv} for patient treatments.</li>
 * </ul>
 */
public class MedicalRecordManager {

    /**
     * File path for storing patient medical records.
     */
    private static final String MEDICAL_RECORDS_FILE = "data/MedicalRecords.csv";

    /**
     * File path for storing diagnoses data.
     */
    private static final String DIAGNOSES_FILE = "data/Diagnoses.csv";

     /**
     * File path for storing treatments data.
     */
    private static final String TREATMENTS_FILE = "data/treatments.csv";

     /**
     * Date formatter for parsing and formatting dates in records.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Singleton instance of the manager.
     */
    private static MedicalRecordManager instance;

     /**
     * Map storing medical records, keyed by patient ID.
     */
    private final Map<String, MedicalRecord> medicalRecords;

    /**
     * Private constructor for singleton implementation.
     * Initializes the medical records map and loads data from files.
     */
    private MedicalRecordManager() {
        medicalRecords = new HashMap<>();
        loadData();
    }

    /**
     * Retrieves the singleton instance of the manager.
     * 
     * @return The singleton instance of {@code MedicalRecordManager}.
     */
    public static MedicalRecordManager getInstance() {
        if (instance == null) {
            instance = new MedicalRecordManager();
        }
        return instance;
    }

    /**
     * Loads all medical records, diagnoses, and treatments from their respective CSV files.
     */
    private void loadData() {
        loadMedicalRecords();
        loadDiagnoses();
        loadTreatments();
    }

     /**
     * Loads basic patient information from the {@code MedicalRecords.csv} file.
     */
    private void loadMedicalRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICAL_RECORDS_FILE))) {
            String header = reader.readLine();  // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    // Parse date using the MM/dd/yyyy format
                    LocalDate dob = LocalDate.parse(data[2].trim(), DATE_FORMATTER);

                    MedicalRecord record = new MedicalRecord(
                            data[0].trim(),      // patientId
                            data[1].trim(),      // name
                            dob,                 // dateOfBirth
                            data[3].trim(),      // gender
                            data[4].trim(),      // phoneNumber
                            data[5].trim(),      // emailAddress
                            data[6].trim()       // bloodType
                    );
                    medicalRecords.put(record.getPatientId(), record);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading medical records: " + e.getMessage());
        }
    }

     /**
     * Loads patient diagnoses from the {@code Diagnoses.csv} file and associates them with medical records.
     */
    private void loadDiagnoses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DIAGNOSES_FILE))) {
            String header = reader.readLine();  // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    String patientId = data[0].trim();
                    LocalDate date = LocalDate.parse(data[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE);

                    Diagnosis diagnosis = new Diagnosis(
                            date,
                            data[2].trim(),      // doctorId
                            data[3].trim(),      // condition
                            data[4].trim()       // notes
                    );

                    MedicalRecord record = medicalRecords.get(patientId);
                    if (record != null) {
                        record.addDiagnosis(diagnosis);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing diagnosis line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading diagnoses: " + e.getMessage());
        }
    }

    /**
     * Loads patient treatments from the {@code Treatments.csv} file and associates them with medical records.
     */
    private void loadTreatments() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TREATMENTS_FILE))) {
            String header = reader.readLine();  // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    String patientId = data[0].trim();
                    LocalDate date = LocalDate.parse(data[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                    List<String> medications = Arrays.asList(data[4].trim().split(";"));

                    Treatment treatment = new Treatment(
                            date,
                            data[2].trim(),      // doctorId
                            data[3].trim(),      // treatmentType
                            medications,          // medications
                            data[5].trim()       // instructions
                    );

                    MedicalRecord record = medicalRecords.get(patientId);
                    if (record != null) {
                        record.addTreatment(treatment);
                    }
                } catch (Exception e) {
                    System.err.println("Error processing treatment line: " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading treatments: " + e.getMessage());
        }
    }

    // Public methods

    /**
     * Retrieves a medical record for a specific patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return The {@link MedicalRecord} object for the given patient, or {@code null} if not found.
     */
    public MedicalRecord getMedicalRecord(String patientId) {
        return medicalRecords.get(patientId);
    }

     /**
     * Updates the contact information (phone number and email) for a specific patient.
     *
     * @param patientId   The unique identifier of the patient.
     * @param phoneNumber The new phone number to update.
     * @param email       The new email address to update.
     * @return {@code true} if the contact information was successfully updated, {@code false} if the patient record was not found.
     */
    public boolean updateContactInfo(String patientId, String phoneNumber, String email) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.setPhoneNumber(phoneNumber);
        record.setEmailAddress(email);
        saveMedicalRecords();
        return true;
    }

    /**
     * Adds a new diagnosis to a specific patient's medical record.
     *
     * @param patientId The unique identifier of the patient.
     * @param diagnosis The {@link Diagnosis} object to add to the patient's record.
     * @return {@code true} if the diagnosis was successfully added, {@code false} if the patient record was not found.
     */
    public boolean addDiagnosis(String patientId, Diagnosis diagnosis) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.addDiagnosis(diagnosis);
        saveDiagnoses();
        return true;
    }

    
    /**
     * Adds a new treatment to a specific patient's medical record.
     *
     * @param patientId The unique identifier of the patient.
     * @param treatment The {@link Treatment} object to add to the patient's record.
     * @return {@code true} if the treatment was successfully added, {@code false} if the patient record was not found.
     */
    public boolean addTreatment(String patientId, Treatment treatment) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.addTreatment(treatment);
        saveTreatments();
        return true;
    }

     /**
     * Saves all medical records to the {@code MedicalRecords.csv} file.
     *
     * This method writes the current in-memory data of medical records to the persistent storage file.
     */
    private void saveMedicalRecords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEDICAL_RECORDS_FILE))) {
            writer.println("PatientId,Name,DateOfBirth,Gender,PhoneNumber,EmailAddress,BloodType");

            for (MedicalRecord record : medicalRecords.values()) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                        record.getPatientId(),
                        record.getName(),
                        record.getDateOfBirth(),
                        record.getGender(),
                        record.getPhoneNumber(),
                        record.getEmailAddress(),
                        record.getBloodType()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving medical records: " + e.getMessage());
        }
    }

    /**
     * Saves all diagnoses to the {@code Diagnoses.csv} file.
     *
     * This method writes the current in-memory diagnoses data to the persistent storage file.
     */
    private void saveDiagnoses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DIAGNOSES_FILE))) {
            writer.println("PatientId,Date,DoctorId,Condition,Notes");

            for (MedicalRecord record : medicalRecords.values()) {
                for (Diagnosis diagnosis : record.getDiagnoses()) {
                    writer.printf("%s,%s,%s,%s,%s%n",
                            record.getPatientId(),
                            diagnosis.getDate(),
                            diagnosis.getDoctorId(),
                            diagnosis.getCondition(),
                            diagnosis.getNotes()
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving diagnoses: " + e.getMessage());
        }
    }

     /**
     * Saves all treatments to the {@code Treatments.csv} file.
     *
     * This method writes the current in-memory treatments data to the persistent storage file.
     */
    private void saveTreatments() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TREATMENTS_FILE))) {
            writer.println("PatientId,Date,DoctorId,TreatmentType,Medications,Instructions");

            for (MedicalRecord record : medicalRecords.values()) {
                for (Treatment treatment : record.getTreatments()) {
                    writer.printf("%s,%s,%s,%s,%s,%s%n",
                            record.getPatientId(),
                            treatment.getDate(),
                            treatment.getDoctorId(),
                            treatment.getTreatmentType(),
                            String.join(";", treatment.getMedications()),
                            treatment.getInstructions()
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving treatments: " + e.getMessage());
        }
    }
}
