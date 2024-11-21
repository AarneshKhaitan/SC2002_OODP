package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to retrieve and display available appointment slots for a doctor or service.
 * 
 * This class allows patients to view all available appointment slots for a specific doctor
 * or service, enabling them to make informed decisions when scheduling or rescheduling
 * appointments. It interacts with the {@link PatientAppointmentController} to fetch the slots
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 */
public class ViewAvailableSlotsAction implements PatientAction {

     /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code ViewAvailableSlotsAction}.
     * 
     * @param appointmentController The controller used to manage patient appointments and fetch available slots.
     */
    public ViewAvailableSlotsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    
    /**
     * Executes the action to display available appointment slots for a specified doctor.
     * 
     * The steps include:
     * <ul>
     *   <li>Prompting the patient to enter the doctor's ID.</li>
     *   <li>Retrieving the available appointment slots for the specified doctor.</li>
     *   <li>Displaying the slots in a formatted list for the patient to review.</li>
     * </ul>
     *
     * If no slots are available, an error message is displayed, and the operation is terminated.
     *
     * @param patient The {@link User} object representing the patient viewing the available slots.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Available Appointment Slots");

        // Prompt for the doctor ID
        String doctorId = UIUtils.promptForString("Enter Doctor ID");

        // Retrieve available slots for the specified doctor
        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlots(doctorId);
        if (availableSlots.isEmpty()) {
            UIUtils.displayError("No available slots found for this doctor.");
            return;
        }

        // Display the available slots
        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
        }
        
        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
