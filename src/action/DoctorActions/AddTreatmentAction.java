package action.DoctorActions;

import util.UIUtils;
import controller.MedicalRecords.MedicalRecordController;
import entity.users.User;

import java.util.ArrayList;
import java.util.List;

public class AddTreatmentAction implements DoctorAction {
    private final String patientId;
    private final MedicalRecordController medicalRecordController;

    public AddTreatmentAction(String patientId) {
        this.patientId = patientId;
        this.medicalRecordController = MedicalRecordController.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Add New Treatment");

        String treatmentType = UIUtils.promptForString("Enter treatment type");

        List<String> medications = new ArrayList<>();
        while (true) {
            String medication = UIUtils.promptForString("Enter medication (or press Enter to finish)");
            if (medication.isEmpty()) break;
            medications.add(medication);
        }

        String instructions = UIUtils.promptForString("Enter treatment instructions");

        if (medicalRecordController.addTreatment(patientId, doctor.getUserId(), treatmentType, medications, instructions)) {
            UIUtils.displaySuccess("Treatment added successfully!");
        } else {
            UIUtils.displayError("Failed to add treatment.");
        }

        UIUtils.pressEnterToContinue();
    }
}
