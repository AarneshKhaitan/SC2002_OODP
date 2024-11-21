package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import entity.users.User;

import java.util.List;

/**
 * Provides functionality to view detailed patient records.
 * This includes accessing diagnosis histories, treatment plans, and other medical data.
 */
public class ViewPatientRecordsAction implements DoctorAction {
    private final MedicalRecordController medicalRecordController;

    public ViewPatientRecordsAction() {
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("View Patient Medical Records");
        String patientId = UIUtils.promptForString("Enter Patient ID");

        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patientId);
        if (record == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

        // Display patient information
        System.out.println("\nPatient Information:");
        System.out.printf("Patient ID: %s%n", record.getPatientId());
        System.out.printf("Name: %s%n", record.getName());
        System.out.printf("Date of Birth: %s%n", record.getDateOfBirth());
        System.out.printf("Gender: %s%n", record.getGender());
        System.out.printf("Blood Type: %s%n", record.getBloodType());
        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
        System.out.printf("Email: %s%n", record.getEmailAddress());

        // Display diagnoses
        System.out.println("\nDiagnoses History:");
        List<Diagnosis> diagnoses = record.getDiagnoses();
        if (diagnoses.isEmpty()) {
            System.out.println("No diagnoses recorded.");
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
                    diagnosis.getNotes()));
        }

        // Display treatments
        System.out.println("\nTreatments History:");
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
                    treatment.getInstructions()));
        }

        UIUtils.pressEnterToContinue();
    }
}
