package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.time.LocalDateTime;
import java.util.List;

public class RescheduleAppointmentAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public RescheduleAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Reschedule Appointment");

        List<Appointment> reschedulableAppointments = appointmentController.getReschedulableAppointments(patient.getUserId());
        if (reschedulableAppointments.isEmpty()) {
            UIUtils.displayError("No appointments available for rescheduling.");
            return;
        }

        System.out.println("\nReschedulable Appointments:");
        AppointmentDisplayUtil.displayAppointments(reschedulableAppointments);

        String appointmentId = UIUtils.promptForString("Enter appointment ID to reschedule (or press Enter to go back)");
        if (appointmentId.isEmpty()) return;

        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlotsForRescheduling(appointmentId);
        if (availableSlots.isEmpty()) {
            UIUtils.displayError("No available slots for rescheduling.");
            return;
        }

        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
        }

        int slotChoice = UIUtils.promptForInt("Select new slot number", 1, availableSlots.size());
        LocalDateTime newDateTime = availableSlots.get(slotChoice - 1).getStartTime();

        if (UIUtils.promptForYesNo("Confirm rescheduling to " + UIUtils.formatDateTime(newDateTime) + "?")) {
            boolean success = appointmentController.rescheduleAppointment(appointmentId, newDateTime);
            if (success) {
                UIUtils.displaySuccess("Appointment rescheduled successfully!");
            } else {
                UIUtils.displayError("Failed to reschedule appointment. Please try again.");
            }
        }

        UIUtils.pressEnterToContinue();
    }
}
