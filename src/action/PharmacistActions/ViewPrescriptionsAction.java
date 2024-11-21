package action.PharmacistActions;

import entity.users.User;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.Prescription;
import entity.Medications.PrescriptionStatus;
import util.AppointmentOutcomeManager;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to retrieve and display pending prescriptions assigned to the pharmacist.
 * 
 * This class allows pharmacists to view detailed information about pending prescriptions,
 * including:
 * <ul>
 *   <li>Appointment details (ID, patient, date, type).</li>
 *   <li>Prescribed medications that are awaiting fulfillment.</li>
 * </ul>
 * It integrates with the {@link AppointmentOutcomeManager} to fetch pending prescriptions and
 * uses {@link UIUtils} to display the information in a user-friendly format.
 */
public class ViewPrescriptionsAction implements PharmacistAction {

     /**
     * Executes the action to display all pending prescriptions assigned to the pharmacist.
     * 
     * The steps include:
     * <ul>
     *   <li>Retrieving all pending prescriptions from the {@link AppointmentOutcomeManager}.</li>
     *   <li>Displaying detailed information for each prescription, grouped by appointment.</li>
     *   <li>Displaying patient and medication details for prescriptions with a "Pending" status.</li>
     * </ul>
     *
     * If no pending prescriptions are found, an error message is displayed.
     *
     * @param pharmacist The {@link User} object representing the pharmacist viewing the prescriptions.
     */
    @Override
    public void execute(User pharmacist) {
        UIUtils.displayHeader("Pending Prescriptions");

        // Retrieve pending prescriptions
        List<AppointmentOutcomeRecord> recordsWithPendingPrescriptions =
                AppointmentOutcomeManager.getInstance().getPendingPrescriptions();

        if (recordsWithPendingPrescriptions.isEmpty()) {
            UIUtils.displayError("No pending prescriptions found.");
            return;
        }

        // Display prescription details grouped by appointment
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
        
        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
