package util;

import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MedicalRecordManager {
    private static final String MEDICAL_RECORDS_FILE = "data/MedicalRecords.csv";
    private static final String DIAGNOSES_FILE = "data/Diagnoses.csv";
    private static final String TREATMENTS_FILE = "data/treatments.csv";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static MedicalRecordManager instance;
    private final Map<String, MedicalRecord> medicalRecords;

    private MedicalRecordManager() {
        medicalRecords = new HashMap<>();
        loadData();
    }

    public static MedicalRecordManager getInstance() {
        if (instance == null) {
            instance = new MedicalRecordManager();
        }
        return instance;
    }

    private void loadData() {
        loadMedicalRecords();
        loadDiagnoses();
        loadTreatments();
    }

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
    public MedicalRecord getMedicalRecord(String patientId) {
        return medicalRecords.get(patientId);
    }

    public boolean updateContactInfo(String patientId, String phoneNumber, String email) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.setPhoneNumber(phoneNumber);
        record.setEmailAddress(email);
        saveMedicalRecords();
        return true;
    }

    public boolean addDiagnosis(String patientId, Diagnosis diagnosis) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.addDiagnosis(diagnosis);
        saveDiagnoses();
        return true;
    }

    public boolean addTreatment(String patientId, Treatment treatment) {
        MedicalRecord record = medicalRecords.get(patientId);
        if (record == null) return false;

        record.addTreatment(treatment);
        saveTreatments();
        return true;
    }

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