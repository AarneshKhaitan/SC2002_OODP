package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

/**
 * Handles the action of updating patient medical records.
 * Allows doctors to modify or add new information to existing records.
 */
public class UpdatePatientRecordsAction implements DoctorAction {
    private final MedicalRecordController medicalRecordController;

    public UpdatePatientRecordsAction() {
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Update Patient Medical Records");
        String patientId = UIUtils.promptForString("Enter Patient ID");

        if (medicalRecordController.getPatientMedicalRecord(patientId) == null) {
            UIUtils.displayError("Medical record not found.");
            return;
        }

        System.out.println("\nUpdate Options:");
        System.out.println("1. Add New Diagnosis");
        System.out.println("2. Add New Treatment");
        System.out.println("3. Back to Main Menu");

        int choice = UIUtils.promptForInt("Choose an option", 1, 3);

        switch (choice) {
            case 1 -> new AddDiagnosisAction(patientId).execute(doctor);
            case 2 -> new AddTreatmentAction(patientId).execute(doctor);
        }
    }
}
