package util;

import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.PrescriptionStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static entity.Medications.Prescription.quantity;

/**
 * Manages the creation, retrieval, and persistence of appointment outcome records and prescriptions.
 * 
 * <p>
 * This class is a singleton that provides centralized management for:
 * <ul>
 *   <li>Loading and saving appointment outcome records and prescription data from files.</li>
 *   <li>Creating new outcome records and updating prescription statuses.</li>
 *   <li>Retrieving records for patients or prescriptions pending fulfillment.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The class interacts with two CSV files:
 * <ul>
 *   <li>{@code outcome_records.csv} for appointment outcome data.</li>
 *   <li>{@code prescriptions.csv} for prescription details.</li>
 * </ul>
 * </p>
 */
public class AppointmentOutcomeManager {

     /**
     * Singleton instance of the manager.
     */
    private static final String OUTCOME_RECORDS_FILE = "data/outcome_records.csv";

     /**
     * File path for outcome records.
     */
    private static final String PRESCRIPTIONS_FILE = "data/prescriptions.csv";

    /**
     * File path for prescriptions.
     */
    private static AppointmentOutcomeManager instance;

    /**
     * Formatter for parsing and formatting date-time values.
     */
    private final Map<String, AppointmentOutcomeRecord> outcomeRecords; // key: appointmentId

    /**
     * Map storing appointment outcome records, keyed by appointment ID.
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

      /**
     * Private constructor for singleton implementation.
     * Initializes the record map and loads data from files.
     */
    private AppointmentOutcomeManager() {
        this.outcomeRecords = new HashMap<>();
        loadData();
    }

    /**
     * Retrieves the singleton instance of the manager.
     * 
     * @return The singleton instance of {@code AppointmentOutcomeManager}.
     */
    public static AppointmentOutcomeManager getInstance() {
        if (instance == null) {
            instance = new AppointmentOutcomeManager();
        }
        return instance;
    }

    
    /**
     * Loads data from the CSV files into memory.
     */
    private void loadData() {
        File recordsFile = new File(OUTCOME_RECORDS_FILE);
        File prescriptionsFile = new File(PRESCRIPTIONS_FILE);

        if (!recordsFile.exists() || !prescriptionsFile.exists()) {
            createFiles();
        }

        loadOutcomeRecords();
        loadPrescriptions();
    }

     /**
     * Creates the default CSV files if they do not exist.
     */
    private void createFiles() {
        try (PrintWriter recordWriter = new PrintWriter(new FileWriter(OUTCOME_RECORDS_FILE))) {
            recordWriter.println("AppointmentId,Date,Type,ConsultationNotes,DoctorId,PatientId");
        } catch (IOException e) {
            System.err.println("Error creating outcome records file: " + e.getMessage());
        }

        try (PrintWriter prescWriter = new PrintWriter(new FileWriter(PRESCRIPTIONS_FILE))) {
            prescWriter.println("AppointmentId,MedicationName,Status");
        } catch (IOException e) {
            System.err.println("Error creating prescriptions file: " + e.getMessage());
        }
    }

    
    /**
     * Loads appointment outcome records from the {@code outcome_records.csv} file.
     */
    private void loadOutcomeRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTCOME_RECORDS_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                        data[0].trim(), // appointmentId
                        LocalDateTime.parse(data[1].trim(), formatter),
                        data[2].trim(), // type
                        data[3].trim(), // notes
                        data[4].trim(), // doctorId
                        data[5].trim()  // patientId
                );
                outcomeRecords.put(record.getAppointmentId(), record);
            }
        } catch (IOException e) {
            System.err.println("Error loading outcome records: " + e.getMessage());
        }
    }

      /**
     * Loads prescriptions from the {@code prescriptions.csv} file and associates them with outcome records.
     */
    private void loadPrescriptions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRESCRIPTIONS_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String appointmentId = data[0].trim();
                String medicationName = data[1].trim();
                PrescriptionStatus status = PrescriptionStatus.valueOf(data[2].trim());

                AppointmentOutcomeRecord record = outcomeRecords.get(appointmentId);
                if (record != null) {
                    record.addPrescription(medicationName,quantity);
                    record.updatePrescriptionStatus(medicationName, status);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
    }

    /**
     * Saves all outcome records to the {@code outcome_records.csv} file.
     * 
     * <p>
     * This method writes the current in-memory data of outcome records to the persistent storage file.
     * </p>
     */
    private void saveOutcomeRecords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTCOME_RECORDS_FILE))) {
            writer.println("AppointmentId,Date,Type,ConsultationNotes,DoctorId,PatientId");
            for (AppointmentOutcomeRecord record : outcomeRecords.values()) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        record.getAppointmentId(),
                        record.getAppointmentDate().format(formatter),
                        record.getAppointmentType(),
                        record.getConsultationNotes(),
                        record.getDoctorId(),
                        record.getPatientId()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving outcome records: " + e.getMessage());
        }
    }

    /**
     * Saves all prescriptions to the {@code prescriptions.csv} file.
     * 
     * <p>
     * This method writes the current in-memory prescription data to the persistent storage file.
     * </p>
     */
    private void savePrescriptions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRESCRIPTIONS_FILE))) {
            writer.println("AppointmentId,MedicationName,Status");
            for (AppointmentOutcomeRecord record : outcomeRecords.values()) {
                for (var prescription : record.getPrescriptions()) {
                    writer.printf("%s,%s,%s%n",
                            record.getAppointmentId(),
                            prescription.getMedicationName(),
                            prescription.getStatus()
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
        }
    }

    // Public methods

     /**
     * Retrieves all outcome records for a specific patient.
     * 
     * @param patientId The unique identifier of the patient.
     * @return A list of {@link AppointmentOutcomeRecord} objects associated with the patient.
     */
    public List<AppointmentOutcomeRecord> getPatientOutcomeRecords(String patientId) {
        return outcomeRecords.values().stream()
                .filter(record -> record.getPatientId().equals(patientId))
                .toList();
    }

    /**
     * Retrieves all outcome records that include pending prescriptions.
     * 
     * <p>
     * Pending prescriptions are those with a {@link PrescriptionStatus#PENDING} status.
     * </p>
     * 
     * @return A list of {@link AppointmentOutcomeRecord} objects containing pending prescriptions.
     */
    public List<AppointmentOutcomeRecord> getPendingPrescriptions() {
        return outcomeRecords.values().stream()
                .filter(record -> record.getPrescriptions().stream()
                        .anyMatch(p -> p.getStatus() == PrescriptionStatus.PENDING))
                .toList();
    }

    /**
     * Creates a new appointment outcome record and saves it to persistent storage.
     * 
     * @param record The {@link AppointmentOutcomeRecord} object to create.
     * @return {@code true} if the record was successfully created, {@code false} if a record
     *         with the same appointment ID already exists.
     */
    public boolean createOutcomeRecord(AppointmentOutcomeRecord record) {
        if (outcomeRecords.containsKey(record.getAppointmentId())) {
            return false;
        }
        outcomeRecords.put(record.getAppointmentId(), record);
        saveOutcomeRecords();
        savePrescriptions();
        return true;
    }

     /**
     * Updates the status of a prescription for a specific appointment.
     * 
     * @param appointmentId The unique identifier of the appointment.
     * @param medicationName The name of the medication to update.
     * @param newStatus The new {@link PrescriptionStatus} to set.
     * @return {@code true} if the prescription status was successfully updated, {@code false} otherwise.
     */
    public boolean updatePrescriptionStatus(String appointmentId, String medicationName,
                                            PrescriptionStatus newStatus) {
        AppointmentOutcomeRecord record = outcomeRecords.get(appointmentId);
        if (record != null && record.updatePrescriptionStatus(medicationName, newStatus)) {
            savePrescriptions();
            return true;
        }
        return false;
    }

    /**
     * Retrieves an outcome record by appointment ID.
     * 
     * @param appointmentId The unique identifier of the appointment.
     * @return The {@link AppointmentOutcomeRecord} object associated with the given ID, or {@code null} if not found.
     */
    public AppointmentOutcomeRecord getOutcomeRecord(String appointmentId) {
        return outcomeRecords.get(appointmentId);
    }
}
