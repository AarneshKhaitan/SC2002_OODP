package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to schedule a new appointment for a patient.
 * 
 * <p>
 * This class allows patients to schedule new appointments by selecting a doctor,
 * choosing an available time slot, and specifying the type of appointment. It
 * interacts with the {@link PatientAppointmentController} to fetch available slots
 * and process the scheduling request, and uses {@link UIUtils} for user interaction
 * in the console-based UI.
 * </p>
 */
public class ScheduleAppointmentAction implements PatientAction {

     /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

     /**
     * Constructs an instance of {@code ScheduleAppointmentAction}.
     * 
     * @param appointmentController The controller used to manage patient appointments.
     */
    public ScheduleAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to schedule a new appointment for the patient.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Prompting the patient to enter the doctor's ID.</li>
     *   <li>Retrieving available appointment slots for the specified doctor.</li>
     *   <li>Displaying the available slots and prompting the patient to choose one.</li>
     *   <li>Prompting the patient to select the type of appointment (e.g., Consultation, X-ray).</li>
     *   <li>Confirming and processing the appointment scheduling request.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If no slots are available or the scheduling process fails, appropriate error messages
     * are displayed.
     * </p>
     * 
     * @param patient The {@link User} object representing the patient scheduling the appointment.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Schedule New Appointment");

          // Prompt for the doctor ID
        String doctorId = UIUtils.promptForString("Enter Doctor ID");

         // Retrieve available slots for the specified doctor
        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlots(doctorId);
        if (availableSlots.isEmpty()) {
            UIUtils.displayError("No available slots found for this doctor.");
            return;
        }

         // Display available slots
        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
        }

         // Prompt the patient to select a slot
        int slotChoice = UIUtils.promptForInt("Select slot number", 1, availableSlots.size());
        AppointmentSlot selectedSlot = availableSlots.get(slotChoice - 1);

        // Prompt the patient to select an appointment type
        System.out.println("\nAppointment Types:");
        System.out.println("1. Consultation");
        System.out.println("2. X-ray");
        System.out.println("3. Blood Test");

        int typeChoice = UIUtils.promptForInt("Select appointment type", 1, 3);
        String type = switch (typeChoice) {
            case 1 -> "Consultation";
            case 2 -> "X-ray";
            case 3 -> "Blood Test";
            default -> throw new IllegalStateException("Unexpected value: " + typeChoice);
        };

        // Process the appointment scheduling request
        boolean success = appointmentController.scheduleAppointment(patient.getUserId(), doctorId, selectedSlot.getStartTime(), type);

         // Display the result of the operation
        if (success) {
            UIUtils.displaySuccess("Appointment scheduled successfully!");
        } else {
            UIUtils.displayError("Failed to schedule appointment. Please try again.");
        }
         // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
