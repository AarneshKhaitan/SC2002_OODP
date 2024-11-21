package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action to add treatment details to a patient's medical record.
 * This action allows doctors to specify the type of treatment, medications, and instructions
 * and ensures that the treatment is associated with the correct patient and diagnosis.
 * 
 * <p>
 * It interacts with the {@link MedicalRecordController} to persist treatment details
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class AddTreatmentAction implements DoctorAction {
    /**
     * The unique identifier of the patient to whom the treatment is being added.
     */
    private final String patientId;
    /**
     * Controller for managing medical record operations.
     */
    private final MedicalRecordController medicalRecordController;
     /**
     * Constructs an instance of {@code AddTreatmentAction}.
     * 
     * @param patientId The unique identifier of the patient to whom the treatment is to be added.
     */
    
    public AddTreatmentAction(String patientId) {
        this.patientId = patientId;
        this.medicalRecordController = MedicalRecordController.getInstance();
    }
    
    /**
     * Executes the action to add a new treatment.
     * Prompts the doctor to enter treatment details, including type, medications, and instructions,
     * and then persists the data using the {@link MedicalRecordController}.
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Add New Treatment");

        // Prompting for treatment details
        String treatmentType = UIUtils.promptForString("Enter treatment type");

         // Collecting medications for the treatment
        List<String> medications = new ArrayList<>();
        while (true) {
            String medication = UIUtils.promptForString("Enter medication (or press Enter to finish)");
            if (medication.isEmpty()) break;
            medications.add(medication);
        }

        // Prompting for treatment instructions
        String instructions = UIUtils.promptForString("Enter treatment instructions");

         // Attempting to add treatment and displaying the result
        if (medicalRecordController.addTreatment(patientId, doctor.getUserId(), treatmentType, medications, instructions)) {
            UIUtils.displaySuccess("Treatment added successfully!");
        } else {
            UIUtils.displayError("Failed to add treatment.");
        }

        // Pausing the UI to allow user to review output
        UIUtils.pressEnterToContinue();
    }
}
