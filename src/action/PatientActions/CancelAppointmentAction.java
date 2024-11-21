package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to cancel a patient's existing appointment.
 * 
 * <p>
 * This class allows patients to view their current appointments, select one to cancel,
 * and confirm the cancellation. It interacts with the {@link PatientAppointmentController}
 * to perform the cancellation and uses {@link UIUtils} for user interaction in the
 * console-based UI.
 * </p>
 */
public class CancelAppointmentAction implements PatientAction {

     /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code CancelAppointmentAction}.
     * 
     * @param appointmentController The controller used to manage patient appointments.
     */
    public CancelAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

      /**
     * Executes the action to cancel a patient's appointment.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving a list of the patient's current appointments.</li>
     *   <li>Filtering out appointments that cannot be canceled (e.g., already canceled or completed).</li>
     *   <li>Displaying cancellable appointments to the patient.</li>
     *   <li>Prompting the patient to select an appointment ID to cancel.</li>
     *   <li>Confirming the cancellation and processing it through the {@link PatientAppointmentController}.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If the patient has no cancellable appointments or the cancellation fails,
     * appropriate error messages are displayed.
     * </p>
     * 
     * @param patient The {@link User} object representing the patient performing the action.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Cancel Appointment");
        
        // Retrieve the patient's appointments
        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            UIUtils.displayError("You have no appointments to cancel.");
            return;
        }

        // Filter and display cancellable appointments
        System.out.println("\nCancellable Appointments:");
        AppointmentDisplayUtil.displayAppointments(appointments.stream()
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
                .toList());

        // Prompt for the appointment ID to cancel
        String appointmentId = UIUtils.promptForString("Enter appointment ID to cancel (or press Enter to go back)");
        if (!appointmentId.isEmpty() && UIUtils.promptForYesNo("Are you sure you want to cancel this appointment?")) {
            // Process the cancellation
            boolean success = appointmentController.cancelAppointment(appointmentId);
            if (success) {
                UIUtils.displaySuccess("Appointment cancelled successfully!");
            } else {
                UIUtils.displayError("Failed to cancel appointment. Please check the ID and try again.");
            }
        }

        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
