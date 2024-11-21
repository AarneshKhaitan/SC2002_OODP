package action.DoctorActions;

import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.users.User;
import util.AppointmentOutcomeManager;
import util.UIUtils;

/**
 * Handles the creation of an appointment outcome.
 * Allows doctors to document the results or outcomes of a completed appointment.
 */
public class CreateAppointmentOutcomeAction implements DoctorAction {
    private final DoctorAppointmentController appointmentController;

    public CreateAppointmentOutcomeAction(DoctorAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Create Appointment Outcome Record");

        String appointmentId = UIUtils.promptForString("Enter Appointment ID");

        Appointment appointment = appointmentController.getAppointment(appointmentId);
        if (appointment == null) {
            UIUtils.displayError("Appointment not found.");
            return;
        }

        String consultationNotes = UIUtils.promptForString("Enter consultation notes");

        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                appointmentId,
                appointment.getDateTime(),
                appointment.getType(),
                consultationNotes,
                doctor.getUserId(),
                appointment.getPatientId()
        );

        while (true) {
            String medication = UIUtils.promptForString(
                    "Enter prescribed medication (or press Enter to finish)");
            if (medication.isEmpty()) break;

            int quantity = UIUtils.promptForInt("Enter quantity for " + medication, 1, 100);
            record.addPrescription(medication, quantity);
        }

        if (AppointmentOutcomeManager.getInstance().createOutcomeRecord(record)) {
            UIUtils.displaySuccess("Appointment outcome record created successfully!");
        } else {
            UIUtils.displayError("Failed to create appointment outcome record.");
        }

        UIUtils.pressEnterToContinue();
    }
}
