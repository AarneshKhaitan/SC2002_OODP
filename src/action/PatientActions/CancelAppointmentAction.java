package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.util.List;

/**
 * Handles the action of canceling an existing appointment.
 * Allows patients to remove appointments they can no longer attend,
 * freeing up slots for others.
 */
public class CancelAppointmentAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public CancelAppointmentAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Cancel Appointment");

        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            UIUtils.displayError("You have no appointments to cancel.");
            return;
        }

        System.out.println("\nCancellable Appointments:");
        AppointmentDisplayUtil.displayAppointments(appointments.stream()
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
                .toList());

        String appointmentId = UIUtils.promptForString("Enter appointment ID to cancel (or press Enter to go back)");
        if (!appointmentId.isEmpty() && UIUtils.promptForYesNo("Are you sure you want to cancel this appointment?")) {
            boolean success = appointmentController.cancelAppointment(appointmentId);
            if (success) {
                UIUtils.displaySuccess("Appointment cancelled successfully!");
            } else {
                UIUtils.displayError("Failed to cancel appointment. Please check the ID and try again.");
            }
        }

        UIUtils.pressEnterToContinue();
    }
}
