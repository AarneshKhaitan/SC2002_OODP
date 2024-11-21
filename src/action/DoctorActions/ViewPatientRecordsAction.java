package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.Records.MedicalRecord;
import entity.Records.Diagnosis;
import entity.Records.Treatment;
import entity.users.User;

import java.util.List;

/**
 * Represents an action for viewing a patient's medical records.
 * 
 * This class allows doctors to access detailed information about a patient's medical history,
 * including personal details, diagnosis history, and treatment records. It retrieves the records
 * using the {@link MedicalRecordController} and formats the data for display using the console-based UI.
 */
public class ViewPatientRecordsAction implements DoctorAction {

     /**
     * Controller for managing medical record operations.
     */
    private final MedicalRecordController medicalRecordController;

     /**
     * Constructs an instance of {@code ViewPatientRecordsAction}.
     * Initializes the medical record controller to handle retrieval of patient records.
     */
    public ViewPatientRecordsAction() {
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

     /**
     * Executes the action to view a patient's medical records.
     * 
     * The steps include:
     * <ul>
     *   <li>Prompting the doctor for the patient ID.</li>
     *   <li>Retrieving the medical record for the specified patient.</li>
     *   <li>Displaying the patient's personal details.</li>
     *   <li>Displaying the diagnosis history, if available.</li>
     *   <li>Displaying the treatment history, if available.</li>
     * </ul>
     *
     * If the patient ID is invalid or the record does not exist, an error is displayed,
     * and the operation is terminated.
     *
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("View Patient Medical Records");

         // Prompt for the patient ID
        String patientId = UIUtils.promptForString("Enter Patient ID");

        // Retrieve the medical record
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

        // Pause to allow the user to review the information
        UIUtils.pressEnterToContinue();
    }
}
