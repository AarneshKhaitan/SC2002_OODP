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
 * Handles updating the status of a prescription.
 * Allows pharmacists to mark prescriptions as filled, in progress, or on hold.
 * Ensures accurate tracking of prescription processing.
 */
public class UpdatePrescriptionStatusAction implements PharmacistAction {
    private final InventoryManager inventoryManager;

    public UpdatePrescriptionStatusAction(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void execute(User pharmacist) {
        UIUtils.displayHeader("Update Prescription Status");

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

            if (AppointmentOutcomeManager.getInstance()
                    .updatePrescriptionStatus(appointmentId, medicationName, newStatus)) {

                // If dispensed, update inventory
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
        UIUtils.pressEnterToContinue();
    }
}
