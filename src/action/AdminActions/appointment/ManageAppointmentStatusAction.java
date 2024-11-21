package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import util.UIUtils;

import java.util.List;

public class ManageAppointmentStatusAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    public ManageAppointmentStatusAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute() {
        UIUtils.displayHeader("Manage Appointment Status");
        List<Appointment> appointments = appointmentController.getAllAppointments();

        if (appointments.isEmpty()) {
            UIUtils.displayError("No appointments found in the system.");
            return;
        }

        System.out.println("\nAll Appointments:");
        displayAppointmentsDetailed(appointments);

        String appointmentId = UIUtils.promptForString(
                "Enter appointment ID to update (or press Enter to go back)");

        if (!appointmentId.isEmpty()) {
            System.out.println("\nSelect new status:");
            System.out.println("1. Requested");
            System.out.println("2. Confirmed");
            System.out.println("3. Cancelled");
            System.out.println("4. Completed");

            int statusChoice = UIUtils.promptForInt("Enter choice", 1, 4);

            Appointment.AppointmentStatus newStatus = switch (statusChoice) {
                case 1 -> Appointment.AppointmentStatus.REQUESTED;
                case 2 -> Appointment.AppointmentStatus.CONFIRMED;
                case 3 -> Appointment.AppointmentStatus.CANCELLED;
                case 4 -> Appointment.AppointmentStatus.COMPLETED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            if (UIUtils.promptForYesNo("Confirm status update?")) {
                boolean success = appointmentController.updateAppointmentStatus(
                        appointmentId, newStatus);

                if (success) {
                    UIUtils.displaySuccess("Appointment status updated successfully!");
                } else {
                    UIUtils.displayError("Failed to update appointment status.");
                }
            }
        }
        UIUtils.pressEnterToContinue();
    }

    private void displayAppointmentsDetailed(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            System.out.printf("""
                ----------------------------------------
                Appointment ID: %s
                Patient ID: %s
                Doctor ID: %s
                Date & Time: %s
                Type: %s
                Status: %s
                ----------------------------------------
                """,
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    UIUtils.formatDateTime(appointment.getDateTime()),
                    appointment.getType(),
                    appointment.getStatus());
        }
    }
}
