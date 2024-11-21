package action.DoctorActions;

import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to update the status of a doctor's appointments.
 * 
 * <p>
 * This class allows doctors to view their active appointments and update their status to
 * "Confirmed," "Completed," or "Cancelled." Upon marking an appointment as completed,
 * it triggers the creation of an appointment outcome record.
 * </p>
 * 
 * <p>
 * The class interacts with the {@link DoctorAppointmentController} to retrieve and update
 * appointment data, and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class UpdateAppointmentStatusAction implements DoctorAction {

    /**
     * Controller for managing doctor appointments.
     */
    private final DoctorAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code UpdateAppointmentStatusAction}.
     * Initializes the appointment controller to handle status updates for appointments.
     */
    public UpdateAppointmentStatusAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    /**
     * Executes the action to update the status of a doctor's appointments.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving the list of active appointments for the doctor.</li>
     *   <li>Displaying the active appointments for selection.</li>
     *   <li>Allowing the doctor to choose a new status for a selected appointment.</li>
     *   <li>Persisting the status update via the {@link DoctorAppointmentController}.</li>
     *   <li>Triggering the creation of an outcome record if the status is set to "Completed."</li>
     * </ul>
     * </p>
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Update Appointment Status");

        // Retrieve all appointments for the doctor
        List<Appointment> appointments = appointmentController.getDoctorAppointments(doctor.getUserId());

        // Filter for active appointments
        List<Appointment> activeAppointments = appointments.stream()
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
                .toList();

        if (activeAppointments.isEmpty()) {
            UIUtils.displayError("No active appointments to update.");
            return;
        }

         // Display active appointments
        System.out.println("\nActive Appointments:");
        displayAppointments(activeAppointments);

        // Prompt for the appointment to update
        String appointmentId = UIUtils.promptForString(
                "Enter appointment ID to update (or press Enter to go back)");

        // Display status options
        if (!appointmentId.isEmpty()) {
            System.out.println("\nSelect new status:");
            System.out.println("1. Confirmed");
            System.out.println("2. Completed");
            System.out.println("3. Cancelled");

            int statusChoice = UIUtils.promptForInt("Enter choice", 1, 3);

               // Map user choice to appointment status
            Appointment.AppointmentStatus newStatus = switch (statusChoice) {
                case 1 -> Appointment.AppointmentStatus.CONFIRMED;
                case 2 -> Appointment.AppointmentStatus.COMPLETED;
                case 3 -> Appointment.AppointmentStatus.CANCELLED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            // Confirm the status update and persist changes
            if (UIUtils.promptForYesNo("Confirm status update?")) {
                boolean success = appointmentController.updateAppointmentStatus(
                        appointmentId, newStatus);

                if (success) {
                    UIUtils.displaySuccess("Appointment status updated successfully!");

                    // Trigger outcome creation for completed appointments
                    if (newStatus == Appointment.AppointmentStatus.COMPLETED) {
                        DoctorAction createOutcomeAction = new CreateAppointmentOutcomeAction(appointmentController);
                        createOutcomeAction.execute(doctor);
                    }
                } else {
                    UIUtils.displayError("Failed to update appointment status.");
                }
            }
        }
        
         // Pause for user review
        UIUtils.pressEnterToContinue();
    }

     /**
     * Displays a list of active appointments.
     * 
     * @param appointments The list of {@link Appointment} objects to display.
     */
    private void displayAppointments(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            System.out.printf("""
                ----------------------------------------
                Appointment ID: %s
                Patient ID: %s
                Date & Time: %s
                Type: %s
                Status: %s
                ----------------------------------------
                """,
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    UIUtils.formatDateTime(appointment.getDateTime()),
                    appointment.getType(),
                    appointment.getStatus());
        }
    }
}
