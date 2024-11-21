package util;

import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.PrescriptionStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static entity.Medications.Prescription.quantity;

public class AppointmentOutcomeManager {
    private static final String OUTCOME_RECORDS_FILE = "data/outcome_records.csv";
    private static final String PRESCRIPTIONS_FILE = "data/prescriptions.csv";
    private static AppointmentOutcomeManager instance;
    private final Map<String, AppointmentOutcomeRecord> outcomeRecords; // key: appointmentId
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AppointmentOutcomeManager() {
        this.outcomeRecords = new HashMap<>();
        loadData();
    }

    public static AppointmentOutcomeManager getInstance() {
        if (instance == null) {
            instance = new AppointmentOutcomeManager();
        }
        return instance;
    }

    private void loadData() {
        File recordsFile = new File(OUTCOME_RECORDS_FILE);
        File prescriptionsFile = new File(PRESCRIPTIONS_FILE);

        if (!recordsFile.exists() || !prescriptionsFile.exists()) {
            createFiles();
        }

        loadOutcomeRecords();
        loadPrescriptions();
    }

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
    public List<AppointmentOutcomeRecord> getPatientOutcomeRecords(String patientId) {
        return outcomeRecords.values().stream()
                .filter(record -> record.getPatientId().equals(patientId))
                .toList();
    }

    public List<AppointmentOutcomeRecord> getPendingPrescriptions() {
        return outcomeRecords.values().stream()
                .filter(record -> record.getPrescriptions().stream()
                        .anyMatch(p -> p.getStatus() == PrescriptionStatus.PENDING))
                .toList();
    }

    public boolean createOutcomeRecord(AppointmentOutcomeRecord record) {
        if (outcomeRecords.containsKey(record.getAppointmentId())) {
            return false;
        }
        outcomeRecords.put(record.getAppointmentId(), record);
        saveOutcomeRecords();
        savePrescriptions();
        return true;
    }

    public boolean updatePrescriptionStatus(String appointmentId, String medicationName,
                                            PrescriptionStatus newStatus) {
        AppointmentOutcomeRecord record = outcomeRecords.get(appointmentId);
        if (record != null && record.updatePrescriptionStatus(medicationName, newStatus)) {
            savePrescriptions();
            return true;
        }
        return false;
    }

    public AppointmentOutcomeRecord getOutcomeRecord(String appointmentId) {
        return outcomeRecords.get(appointmentId);
    }
}