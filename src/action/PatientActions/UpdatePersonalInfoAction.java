package action.PatientActions;

import controller.MedicalRecords.MedicalRecordController;
import entity.Records.MedicalRecord;
import entity.users.User;
import util.UIUtils;

/**
 * Represents an action to update a patient's personal information.
 * 
 * <p>
 * This class allows patients to view and update their personal contact details,
 * such as their phone number and email address. It interacts with the
 * {@link MedicalRecordController} to retrieve and persist the updated information
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class UpdatePersonalInfoAction implements PatientAction {

     /**
     * Controller for managing patient medical records.
     */
    private final MedicalRecordController medicalRecordController;

    /**
     * Constructs an instance of {@code UpdatePersonalInfoAction}.
     * 
     * @param medicalRecordController The controller used to manage medical records.
     */
    public UpdatePersonalInfoAction(MedicalRecordController medicalRecordController) {
        this.medicalRecordController = medicalRecordController;
    }

    /**
     * Executes the action to update a patient's personal contact information.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving the patient's current medical record.</li>
     *   <li>Displaying the current phone number and email address.</li>
     *   <li>Prompting the patient to enter new values or retain the existing ones.</li>
     *   <li>Updating the contact information in the medical record system.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If the patient's medical record cannot be found or the update fails, an error message
     * is displayed.
     * </p>
     * 
     * @param patient The {@link User} object representing the patient updating their personal information.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Update Personal Information");

        // Retrieve the patient's medical record
        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patient.getUserId());
        if (record == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

         // Display current contact information
        System.out.println("\nCurrent Contact Information:");
        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
        System.out.printf("Email: %s%n", record.getEmailAddress());

        // Prompt the patient to update their contact information
        String phoneNumber = UIUtils.promptForString("\nEnter new phone number (or press Enter to keep current)");
        String email = UIUtils.promptForString("Enter new email (or press Enter to keep current)");

        // Update the contact information if changes are provided
        if (!phoneNumber.isEmpty() || !email.isEmpty()) {
            String newPhone = phoneNumber.isEmpty() ? record.getPhoneNumber() : phoneNumber;
            String newEmail = email.isEmpty() ? record.getEmailAddress() : email;

            if (medicalRecordController.updateContactInfo(patient.getUserId(), newPhone, newEmail)) {
                UIUtils.displaySuccess("Contact information updated successfully!");
            } else {
                UIUtils.displayError("Failed to update contact information.");
            }
        }

         // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
