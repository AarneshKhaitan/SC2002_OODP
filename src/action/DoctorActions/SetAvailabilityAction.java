package action.DoctorActions;

import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import controller.interfaces.DoctorAppointmentController;
import entity.users.User;
import util.UIUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action for managing a doctor's availability settings within the system.
 * 
 * <p>
 * This class allows doctors to configure their availability by specifying time slots when they
 * are available for appointments. It validates the input to ensure slots are in the correct format
 * and are not set in the past.
 * </p>
 * 
 * <p>
 * The class interacts with the {@link DoctorAppointmentController} to persist availability settings
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class SetAvailabilityAction implements DoctorAction {
    
    /**
     * Controller for managing doctor appointments and availability.
     */
    private final DoctorAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code SetAvailabilityAction}.
     * Initializes the appointment controller to handle doctor availability settings.
     */
    public SetAvailabilityAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    /**
     * Executes the action to set a doctor's availability.
     * 
     * <p>
     * Prompts the doctor to input time slots in the format "yyyy-MM-dd HH:mm". Validates each input to
     * ensure it is in the correct format and is not set in the past. Once all slots are entered, it
     * saves them using the {@link DoctorAppointmentController}.
     * </p>
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Prompting the doctor to input availability slots.</li>
     *   <li>Validating the slots for format and time constraints.</li>
     *   <li>Saving the slots to the system and displaying the operation result.</li>
     * </ul>
     * </p>
     * 
     * @param doctor The {@link User} object representing the doctor setting their availability.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Set Availability");
        List<LocalDateTime> slots = new ArrayList<>();

        System.out.println("\nEnter time slots for availability:");
        System.out.println("Format: yyyy-MM-dd HH:mm");
        System.out.println("Enter 'done' when finished.\n");

        // Collect availability slots from the doctor
        while (true) {
            String input = UIUtils.promptForString("Enter slot datetime (or 'done' to finish)");

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                 // Validate that the slot is not in the past
                if (dateTime.isBefore(LocalDateTime.now())) {
                    UIUtils.displayError("Cannot add slots in the past.");
                    continue;
                }

                slots.add(dateTime);
                UIUtils.displaySuccess("Slot added successfully.");
            } catch (Exception e) {
                UIUtils.displayError("Invalid date format. Please use yyyy-MM-dd HH:mm.");
            }
        }

         // Save the slots if any were added
        if (!slots.isEmpty()) {
            boolean success = appointmentController.addDoctorSlots(doctor.getUserId(), slots);
            if (success) {
                UIUtils.displaySuccess("Availability slots added successfully!");
            } else {
                UIUtils.displayError("Failed to add availability slots.");
            }
        } else {
            UIUtils.displayError("No slots added.");
        }

         // Pause to allow the user to review the output
        UIUtils.pressEnterToContinue();
    }
}
