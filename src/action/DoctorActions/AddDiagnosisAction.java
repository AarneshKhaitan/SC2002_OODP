package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

/**
 * Represents an action to add diagnostic information to a patient's medical records.
 * This action allows doctors to create new diagnosis entries for a specific patient,
 * including the condition and any additional notes.
 * 
 * <p>
 * It interacts with the {@link MedicalRecordController} to persist diagnosis information
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class AddDiagnosisAction implements DoctorAction {
    /**
     * The unique identifier of the patient to whom the diagnosis is being added.
     */
    private final String patientId;
    /**
     * Controller for managing medical record operations.
     */
    private final MedicalRecordController medicalRecordController;
    /**
     * Constructs an instance of {@code AddDiagnosisAction}.
     * 
     * @param patientId The unique identifier of the patient to whom the diagnosis is to be added.
     */
    public AddDiagnosisAction(String patientId) {
        this.patientId = patientId;
        this.medicalRecordController = MedicalRecordController.getInstance();
    }
    /**
     * Executes the action to add a new diagnosis.
     * Prompts the doctor to enter the condition/diagnosis and additional notes,
     * and then persists the data using the {@link MedicalRecordController}.
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Add New Diagnosis");
        
        // Prompting for diagnosis details
        String condition = UIUtils.promptForString("Enter condition/diagnosis");
        String notes = UIUtils.promptForString("Enter additional notes");

         // Attempting to add diagnosis and displaying the result
        if (medicalRecordController.addDiagnosis(patientId, doctor.getUserId(), condition, notes)) {
            UIUtils.displaySuccess("Diagnosis added successfully!");
        } else {
            UIUtils.displayError("Failed to add diagnosis.");
        }

         // Pausing the UI to allow user to review output
        UIUtils.pressEnterToContinue();
    }
}
