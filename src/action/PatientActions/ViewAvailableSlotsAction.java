package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.UIUtils;

import java.util.List;

/**
 * Retrieves and displays available appointment slots for a doctor or service.
 * Allows patients to choose an appropriate time for scheduling or rescheduling appointments.
 */
public class ViewAvailableSlotsAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public ViewAvailableSlotsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Available Appointment Slots");
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
        UIUtils.pressEnterToContinue();
    }
}
