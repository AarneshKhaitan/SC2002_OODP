package action.PharmacistActions;

import entity.users.User;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.Medication;
import entity.Medications.Prescription;
import entity.Medications.PrescriptionStatus;
import util.AppointmentOutcomeManager;
import util.InventoryManager;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to update the status of prescriptions.
 * 
 * <p>
 * This class allows pharmacists to process pending prescriptions by updating their status
 * to "Dispensed" or "Cancelled." It ensures accurate tracking of prescriptions and integrates
 * with the inventory system to adjust stock levels when medications are dispensed.
 * </p>
 * 
 * <p>
 * The class interacts with the {@link AppointmentOutcomeManager} to retrieve and update
 * prescription data and the {@link InventoryManager} to manage medication stock.
 * </p>
 */
public class UpdatePrescriptionStatusAction implements PharmacistAction {

     /**
     * Manager for handling inventory operations, including updating medication stock.
     */
    private final InventoryManager inventoryManager;

     /**
     * Constructs an instance of {@code UpdatePrescriptionStatusAction}.
     * 
     * @param inventoryManager The inventory manager used to update medication stock.
     */
    public UpdatePrescriptionStatusAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

     /**
     * Executes the action to update the status of prescriptions.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving all pending prescriptions from the {@link AppointmentOutcomeManager}.</li>
     *   <li>Displaying pending prescriptions to the pharmacist.</li>
     *   <li>Allowing the pharmacist to select an appointment and a specific prescription.</li>
     *   <li>Prompting the pharmacist to update the prescription status to "Dispensed" or "Cancelled."</li>
     *   <li>Updating the inventory stock if the prescription is marked as "Dispensed."</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If no pending prescriptions are found, or if the update fails, appropriate error messages
     * are displayed.
     * </p>
     * 
     * @param pharmacist The {@link User} object representing the pharmacist performing the action.
     */
    @Override
    public void execute(User pharmacist) {
        UIUtils.displayHeader("Update Prescription Status");

        // Retrieve pending prescriptions
        List<AppointmentOutcomeRecord> recordsWithPendingPrescriptions =
                AppointmentOutcomeManager.getInstance().getPendingPrescriptions();

        if (recordsWithPendingPrescriptions.isEmpty()) {
            UIUtils.displayError("No pending prescriptions found.");
            return;
        }

        // Display all pending prescriptions
        System.out.println("\nPending Prescriptions:");
        for (AppointmentOutcomeRecord record : recordsWithPendingPrescriptions) {
            for (Prescription prescription : record.getPrescriptions()) {
                if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                    System.out.printf("""
                    ----------------------------------------
                    Appointment ID: %s
                    Patient ID: %s
                    Medication: %s
                    Quantity: %d
                    Date Prescribed: %s
                    ----------------------------------------
                    """,
                            record.getAppointmentId(),
                            record.getPatientId(),
                            prescription.getMedicationName(),
                            prescription.getQuantity(),
                            UIUtils.formatDateTime(record.getAppointmentDate())
                    );
                }
            }
        }

        // Get user input for updating status
        String appointmentId = UIUtils.promptForString(
                "Enter Appointment ID (or press Enter to go back)");

        if (!appointmentId.isEmpty()) {
            AppointmentOutcomeRecord record =
                    AppointmentOutcomeManager.getInstance().getOutcomeRecord(appointmentId);

            if (record == null) {
                UIUtils.displayError("Invalid Appointment ID.");
                return;
            }

            String medicationName = UIUtils.promptForString("Enter medication name");
            System.out.println("\nSelect new status:");
            System.out.println("1. Dispensed");
            System.out.println("2. Cancelled");

            int choice = UIUtils.promptForInt("Enter choice", 1, 2);
            PrescriptionStatus newStatus = (choice == 1) ?
                    PrescriptionStatus.DISPENSED : PrescriptionStatus.CANCELLED;

             // Update prescription status
            if (AppointmentOutcomeManager.getInstance()
                    .updatePrescriptionStatus(appointmentId, medicationName, newStatus)) {

                // Adjust inventory if the prescription is dispensed
                if (newStatus == PrescriptionStatus.DISPENSED) {
                    for (Medication medication : inventoryManager.getMedications()) {
                        if (medication.getName().equalsIgnoreCase(medicationName)) {
                            medication.decreaseStock(1); // Assuming 1 unit per prescription
                            InventoryManager.saveMedications(inventoryManager.getMedications());
                            break;
                        }
                    }
                }

                UIUtils.displaySuccess("Prescription status updated successfully!");
            } else {
                UIUtils.displayError("Failed to update prescription status. " +
                        "Please check the medication name and try again.");
            }
        }
         // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
