package action.PatientActions;

import controller.MedicalRecords.MedicalRecordController;
import entity.Records.Diagnosis;
import entity.Records.MedicalRecord;
import entity.Records.Treatment;
import entity.users.User;
import util.UIUtils;
import java.util.List;


public class ViewMedicalRecordAction implements PatientAction {
    private final MedicalRecordController medicalRecordController;

    public ViewMedicalRecordAction(MedicalRecordController medicalRecordController) {
        this.medicalRecordController = medicalRecordController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Medical Record");

        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patient.getUserId());
        if (record == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

        System.out.println("\nPersonal Information:");
        System.out.printf("Patient ID: %s%n", record.getPatientId());
        System.out.printf("Name: %s%n", record.getName());
        System.out.printf("Date of Birth: %s%n", record.getDateOfBirth());
        System.out.printf("Gender: %s%n", record.getGender());
        System.out.printf("Blood Type: %s%n", record.getBloodType());
        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
        System.out.printf("Email: %s%n", record.getEmailAddress());

        // Display diagnoses
        System.out.println("\nPast Diagnoses:");
        List<Diagnosis> diagnoses = record.getDiagnoses();
        if (diagnoses.isEmpty()) {
            System.out.println("No past diagnoses recorded.");
        } else {
            diagnoses.forEach(diagnosis -> System.out.printf("""
                            Date: %s
                            Doctor: %s
                            Condition: %s
                            Notes: %s
                            ----------------------------------------
                            """,
                    diagnosis.getDate(),
                    diagnosis.getDoctorId(),
                    diagnosis.getCondition(),
                    diagnosis.getNotes()
            ));
        }

        // Display treatments
        System.out.println("\nTreatments:");
        List<Treatment> treatments = record.getTreatments();
        if (treatments.isEmpty()) {
            System.out.println("No treatments recorded.");
        } else {
            treatments.forEach(treatment -> System.out.printf("""
                            Date: %s
                            Doctor: %s
                            Type: %s
                            Medications: %s
                            Instructions: %s
                            ----------------------------------------
                            """,
                    treatment.getDate(),
                    treatment.getDoctorId(),
                    treatment.getTreatmentType(),
                    String.join(", ", treatment.getMedications()),
                    treatment.getInstructions()
            ));
        }
        UIUtils.pressEnterToContinue();
    }
}
