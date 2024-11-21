package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an action to reschedule a patient's existing appointment.
 * 
 * This class allows patients to select a new date and time for an appointment from
 * available slots. It interacts with the {@link PatientAppointmentController} to
 * retrieve reschedulable appointments and available slots, and uses {@link UIUtils}
 * for user interaction in the console-based UI.
 */
public class RescheduleAppointmentAction implements PatientAction {

     /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code RescheduleAppointmentAction}.
     * 
     * @param appointmentController The controller used to manage patient appointments.
     */
    public RescheduleAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

     /**
     * Executes the action to reschedule a patient's appointment.
     * 
     * The steps include:
     * <ul>
     *   <li>Retrieving the list of reschedulable appointments for the patient.</li>
     *   <li>Displaying the reschedulable appointments and prompting the patient to select one.</li>
     *   <li>Fetching available slots for the selected appointment.</li>
     *   <li>Displaying the available slots and prompting the patient to choose a new date and time.</li>
     *   <li>Confirming and processing the rescheduling request.</li>
     * </ul>
     *
     * If no appointments are available for rescheduling or no slots are available,
     * appropriate error messages are displayed, and the operation is terminated.
     *
     * @param patient The {@link User} object representing the patient performing the action.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Reschedule Appointment");

        // Retrieve the list of appointments available for rescheduling
        List<Appointment> reschedulableAppointments = appointmentController.getReschedulableAppointments(patient.getUserId());
        if (reschedulableAppointments.isEmpty()) {
            UIUtils.displayError("No appointments available for rescheduling.");
            return;
        }

         // Display the reschedulable appointments
        System.out.println("\nReschedulable Appointments:");
        AppointmentDisplayUtil.displayAppointments(reschedulableAppointments);

         // Prompt the patient to select an appointment ID
        String appointmentId = UIUtils.promptForString("Enter appointment ID to reschedule (or press Enter to go back)");
        if (appointmentId.isEmpty()) return;

        // Retrieve available slots for the selected appointment
        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlotsForRescheduling(appointmentId);
        if (availableSlots.isEmpty()) {
            UIUtils.displayError("No available slots for rescheduling.");
            return;
        }

         // Display the available slots
        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
        }

         // Prompt the patient to select a new slot
        int slotChoice = UIUtils.promptForInt("Select new slot number", 1, availableSlots.size());
        LocalDateTime newDateTime = availableSlots.get(slotChoice - 1).getStartTime();

        // Confirm and process the rescheduling request
        if (UIUtils.promptForYesNo("Confirm rescheduling to " + UIUtils.formatDateTime(newDateTime) + "?")) {
            boolean success = appointmentController.rescheduleAppointment(appointmentId, newDateTime);
            if (success) {
                UIUtils.displaySuccess("Appointment rescheduled successfully!");
            } else {
                UIUtils.displayError("Failed to reschedule appointment. Please try again.");
            }
        }

        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
