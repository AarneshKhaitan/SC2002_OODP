package action.PatientActions;

import controller.MedicalRecords.MedicalRecordController;
import entity.Records.Diagnosis;
import entity.Records.MedicalRecord;
import entity.Records.Treatment;
import entity.users.User;
import util.UIUtils;
import java.util.List;

/**
 * Represents an action to view a patient's complete medical record.
 * 
 * This class provides patients with access to their personal medical records, including:
 * <ul>
 *   <li>Personal details such as name, date of birth, and contact information.</li>
 *   <li>Past diagnoses with details such as condition, doctor, and notes.</li>
 *   <li>Treatment history including type, medications, and instructions.</li>
 * </ul>
 *
 * The class interacts with the {@link MedicalRecordController} to retrieve medical records
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 */
public class ViewMedicalRecordAction implements PatientAction {

    /**
     * Controller for managing patient medical records.
     */
    private final MedicalRecordController medicalRecordController;

     /**
     * Constructs an instance of {@code ViewMedicalRecordAction}.
     * 
     * @param medicalRecordController The controller used to manage and retrieve medical records.
     */
    public ViewMedicalRecordAction(MedicalRecordController medicalRecordController) {
        this.medicalRecordController = medicalRecordController;
    }

    /**
     * Executes the action to display the patient's complete medical record.
     * 
     * The steps include:
     * <ul>
     *   <li>Retrieving the patient's medical record from the system.</li>
     *   <li>Displaying personal information including contact details and medical identifiers.</li>
     *   <li>Displaying past diagnoses with detailed information.</li>
     *   <li>Displaying treatment history, including medications and instructions.</li>
     * </ul>
     *
     * If the medical record cannot be found, an error message is displayed.
     *
     * @param patient The {@link User} object representing the patient viewing their medical record.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Medical Record");

        // Retrieve the patient's medical record
        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patient.getUserId());
        if (record == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

         // Display personal information
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
        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
