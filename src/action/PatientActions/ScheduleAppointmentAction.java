package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.UIUtils;

import java.util.List;

public class ScheduleAppointmentAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public ScheduleAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Schedule New Appointment");
        String doctorId = UIUtils.promptForString("Enter Doctor ID");

        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlots(doctorId);
        if (availableSlots.isEmpty()) {
            UIUtils.displayError("No available slots found for this doctor.");
            return;
        }

        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
        }

        int slotChoice = UIUtils.promptForInt("Select slot number", 1, availableSlots.size());
        AppointmentSlot selectedSlot = availableSlots.get(slotChoice - 1);

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

        boolean success = appointmentController.scheduleAppointment(patient.getUserId(), doctorId, selectedSlot.getStartTime(), type);

        if (success) {
            UIUtils.displaySuccess("Appointment scheduled successfully!");
        } else {
            UIUtils.displayError("Failed to schedule appointment. Please try again.");
        }
        UIUtils.pressEnterToContinue();
    }
}
