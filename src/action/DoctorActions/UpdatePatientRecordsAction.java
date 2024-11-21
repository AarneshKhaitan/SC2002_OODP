package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

/**
 * Represents an action for updating patient medical records.
 * 
 * <p>
 * This class allows doctors to modify patient medical records by adding new diagnoses
 * or treatments. It ensures that the modifications are appropriately associated with the
 * patient's existing records. The class interacts with the {@link MedicalRecordController}
 * to retrieve and update medical records and uses {@link UIUtils} for user interaction
 * in the console-based UI.
 * </p>
 */
public class UpdatePatientRecordsAction implements DoctorAction {

    /**
     * Controller for managing medical record operations.
     */
    private final MedicalRecordController medicalRecordController;

    /**
     * Constructs an instance of {@code UpdatePatientRecordsAction}.
     * Initializes the medical record controller to handle updates to patient records.
     */
    public UpdatePatientRecordsAction() {
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

    /**
     * Executes the action to update a patient's medical records.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Prompting the doctor for the patient ID.</li>
     *   <li>Checking if the patient's medical record exists in the system.</li>
     *   <li>Providing options to add a new diagnosis or treatment to the record.</li>
     *   <li>Executing the chosen operation using the corresponding action class.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If the patient ID is invalid or the record does not exist, an error is displayed,
     * and the operation is terminated.
     * </p>
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Update Patient Medical Records");

        // Prompt for the patient ID
        String patientId = UIUtils.promptForString("Enter Patient ID");

         // Check if the patient's medical record exists
        if (medicalRecordController.getPatientMedicalRecord(patientId) == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

        // Display update options
        System.out.println("\nUpdate Options:");
        System.out.println("1. Add New Diagnosis");
        System.out.println("2. Add New Treatment");
        System.out.println("3. Back to Main Menu");

        // Prompt for the doctor's choice
        int choice = UIUtils.promptForInt("Choose an option", 1, 3);

        // Execute the corresponding action based on the choice
        switch (choice) {
            case 1 -> new AddDiagnosisAction(patientId).execute(doctor);
            case 2 -> new AddTreatmentAction(patientId).execute(doctor);
        }
    }
}
