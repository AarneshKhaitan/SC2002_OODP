package action.DoctorActions;

import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.users.User;
import util.AppointmentOutcomeManager;
import util.UIUtils;

/**
 * Represents an action to create and store an outcome record for a completed appointment.
 * 
 * <p>
 * This action allows doctors to document key details of the appointment, including
 * consultation notes, prescribed medications, and follow-up requirements.
 * It interacts with the {@link DoctorAppointmentController} to retrieve appointment details,
 * and uses the {@link AppointmentOutcomeManager} to persist the outcome record.
 * </p>
 */
public class CreateAppointmentOutcomeAction implements DoctorAction {
     /**
     * Controller for managing doctor appointments.
     */
    private final DoctorAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code CreateAppointmentOutcomeAction}.
     * 
     * @param appointmentController The controller used to manage and retrieve appointment information.
     */
    public CreateAppointmentOutcomeAction(DoctorAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

     /**
     * Executes the action to create a new appointment outcome record.
     * Prompts the doctor to provide consultation notes, prescribed medications,
     * and other relevant details, then persists the record using the {@link AppointmentOutcomeManager}.
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Create Appointment Outcome Record");

        // Prompt for the appointment ID and retrieve the appointment
        String appointmentId = UIUtils.promptForString("Enter Appointment ID");

        Appointment appointment = appointmentController.getAppointment(appointmentId);
        if (appointment == null) {
            UIUtils.displayError("Appointment not found.");
            return;
        }
        
        // Collect consultation notes from the doctor
        String consultationNotes = UIUtils.promptForString("Enter consultation notes");

         // Create an appointment outcome record
        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                appointmentId,
                appointment.getDateTime(),
                appointment.getType(),
                consultationNotes,
                doctor.getUserId(),
                appointment.getPatientId()
        );

        // Collect prescribed medications
        while (true) {
            String medication = UIUtils.promptForString(
                    "Enter prescribed medication (or press Enter to finish)");
            if (medication.isEmpty()) break;

            int quantity = UIUtils.promptForInt("Enter quantity for " + medication, 1, 100);
            record.addPrescription(medication, quantity);
        }

         // Attempt to save the outcome record and display the result
        if (AppointmentOutcomeManager.getInstance().createOutcomeRecord(record)) {
            UIUtils.displaySuccess("Appointment outcome record created successfully!");
        } else {
            UIUtils.displayError("Failed to create appointment outcome record.");
        }

        // Pause to allow the user to review the output
        UIUtils.pressEnterToContinue();
    }
}
