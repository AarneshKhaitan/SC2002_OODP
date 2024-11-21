package action.PharmacistActions;

import entity.users.User;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.Prescription;
import entity.Medications.PrescriptionStatus;
import util.AppointmentOutcomeManager;
import util.UIUtils;

import java.util.List;

public class ViewPrescriptionsAction implements PharmacistAction {
    @Override
    public void execute(User pharmacist) {
        UIUtils.displayHeader("Pending Prescriptions");

        List<AppointmentOutcomeRecord> recordsWithPendingPrescriptions =
                AppointmentOutcomeManager.getInstance().getPendingPrescriptions();

        if (recordsWithPendingPrescriptions.isEmpty()) {
            UIUtils.displayError("No pending prescriptions found.");
            return;
        }

        for (AppointmentOutcomeRecord record : recordsWithPendingPrescriptions) {
            System.out.printf("""
            ----------------------------------------
            Appointment ID: %s
            Patient ID: %s
            Date: %s
            Type: %s
            
            Pending Prescriptions:
            """,
                    record.getAppointmentId(),
                    record.getPatientId(),
                    UIUtils.formatDateTime(record.getAppointmentDate()),
                    record.getAppointmentType()
            );

            for (Prescription prescription : record.getPrescriptions()) {
                if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                    System.out.printf("- %s%n", prescription.getMedicationName());
                }
            }
            System.out.println("----------------------------------------");
        }
        UIUtils.pressEnterToContinue();
    }
}
