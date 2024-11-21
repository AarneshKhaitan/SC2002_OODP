package controller.MedicalRecords;

import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import util.MedicalRecordManager;
import java.time.LocalDate;
import java.util.List;

public class MedicalRecordController {
    private static MedicalRecordController instance;
    private final MedicalRecordManager medicalRecordManager;

    private MedicalRecordController() {
        this.medicalRecordManager = MedicalRecordManager.getInstance();
    }

    public static MedicalRecordController getInstance() {
        if (instance == null) {
            instance = new MedicalRecordController();
        }
        return instance;
    }

    // Patient methods
    public MedicalRecord getPatientMedicalRecord(String patientId) {
        return medicalRecordManager.getMedicalRecord(patientId);
    }

    public boolean updateContactInfo(String patientId, String phoneNumber, String email) {
        return medicalRecordManager.updateContactInfo(patientId, phoneNumber, email);
    }

    // Doctor methods
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