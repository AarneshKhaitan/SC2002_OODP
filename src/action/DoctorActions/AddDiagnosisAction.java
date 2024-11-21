package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

/**
 * Represents the action of adding a diagnosis to a patient's medical record.
 * Allows doctors to document new health conditions or symptoms identified during an appointment.
 */
public class AddDiagnosisAction implements DoctorAction {
    private final String patientId;
    private final MedicalRecordController medicalRecordController;

    public AddDiagnosisAction(String patientId) {
        this.patientId = patientId;
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Add New Diagnosis");

        String condition = UIUtils.promptForString("Enter condition/diagnosis");
        String notes = UIUtils.promptForString("Enter additional notes");

        if (medicalRecordController.addDiagnosis(patientId, doctor.getUserId(), condition, notes)) {
            UIUtils.displaySuccess("Diagnosis added successfully!");
        } else {
            UIUtils.displayError("Failed to add diagnosis.");
        }

        UIUtils.pressEnterToContinue();
    }
}
