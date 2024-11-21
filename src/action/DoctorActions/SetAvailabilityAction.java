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
 * Handles the action of setting a doctor's availability.
 * Allows doctors to define their working hours and availability for appointments.
 */
public class SetAvailabilityAction implements DoctorAction {
    private final DoctorAppointmentController appointmentController;

    public SetAvailabilityAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Set Availability");
        List<LocalDateTime> slots = new ArrayList<>();

        System.out.println("\nEnter time slots for availability:");
        System.out.println("Format: yyyy-MM-dd HH:mm");
        System.out.println("Enter 'done' when finished.\n");

        while (true) {
            String input = UIUtils.promptForString("Enter slot datetime (or 'done' to finish)");

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

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

        UIUtils.pressEnterToContinue();
    }
}
