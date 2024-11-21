package action.DoctorActions;

import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.UIUtils;

import java.util.List;

public class UpdateAppointmentStatusAction implements DoctorAction {
    private final DoctorAppointmentController appointmentController;

    public UpdateAppointmentStatusAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Update Appointment Status");
        List<Appointment> appointments = appointmentController.getDoctorAppointments(doctor.getUserId());

        // Filter for active appointments
        List<Appointment> activeAppointments = appointments.stream()
                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
                .toList();

        if (activeAppointments.isEmpty()) {
            UIUtils.displayError("No active appointments to update.");
            return;
        }

        System.out.println("\nActive Appointments:");
        displayAppointments(activeAppointments);

        String appointmentId = UIUtils.promptForString(
                "Enter appointment ID to update (or press Enter to go back)");

        if (!appointmentId.isEmpty()) {
            System.out.println("\nSelect new status:");
            System.out.println("1. Confirmed");
            System.out.println("2. Completed");
            System.out.println("3. Cancelled");

            int statusChoice = UIUtils.promptForInt("Enter choice", 1, 3);

            Appointment.AppointmentStatus newStatus = switch (statusChoice) {
                case 1 -> Appointment.AppointmentStatus.CONFIRMED;
                case 2 -> Appointment.AppointmentStatus.COMPLETED;
                case 3 -> Appointment.AppointmentStatus.CANCELLED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            if (UIUtils.promptForYesNo("Confirm status update?")) {
                boolean success = appointmentController.updateAppointmentStatus(
                        appointmentId, newStatus);

                if (success) {
                    UIUtils.displaySuccess("Appointment status updated successfully!");

                    // Trigger outcome creation for completed appointments
                    if (newStatus == Appointment.AppointmentStatus.COMPLETED) {
                        DoctorAction createOutcomeAction = new CreateAppointmentOutcomeAction(appointmentController);
                        createOutcomeAction.execute(doctor);
                    }
                } else {
                    UIUtils.displayError("Failed to update appointment status.");
                }
            }
        }
        UIUtils.pressEnterToContinue();
    }

    private void displayAppointments(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            System.out.printf("""
                ----------------------------------------
                Appointment ID: %s
                Patient ID: %s
                Date & Time: %s
                Type: %s
                Status: %s
                ----------------------------------------
                """,
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    UIUtils.formatDateTime(appointment.getDateTime()),
                    appointment.getType(),
                    appointment.getStatus());
        }
    }
}
