package action.PatientActions;

import controller.MedicalRecords.MedicalRecordController;
import entity.Records.MedicalRecord;
import entity.users.User;
import util.UIUtils;

/**
 * Manages the update of personal information for a patient.
 * Enables patients to modify their contact details, email address,
 * and other personal information stored in the system.
 */
public class UpdatePersonalInfoAction implements PatientAction {
    private final MedicalRecordController medicalRecordController;

    public UpdatePersonalInfoAction(MedicalRecordController medicalRecordController) {
        this.medicalRecordController = medicalRecordController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Update Personal Information");

        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patient.getUserId());
        if (record == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

        System.out.println("\nCurrent Contact Information:");
        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
        System.out.printf("Email: %s%n", record.getEmailAddress());

        String phoneNumber = UIUtils.promptForString("\nEnter new phone number (or press Enter to keep current)");
        String email = UIUtils.promptForString("Enter new email (or press Enter to keep current)");

        if (!phoneNumber.isEmpty() || !email.isEmpty()) {
            String newPhone = phoneNumber.isEmpty() ? record.getPhoneNumber() : phoneNumber;
            String newEmail = email.isEmpty() ? record.getEmailAddress() : email;

            if (medicalRecordController.updateContactInfo(patient.getUserId(), newPhone, newEmail)) {
                UIUtils.displaySuccess("Contact information updated successfully!");
            } else {
                UIUtils.displayError("Failed to update contact information.");
            }
        }

        UIUtils.pressEnterToContinue();
    }
}
