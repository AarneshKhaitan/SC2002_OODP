package action.AdminActions.appointment;

import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import util.UIUtils;
import action.AdminActions.AdminAction;

import java.util.List;

/**
 * Provides functionality to view appointments filtered by their status.
 * <p>
 * This action allows administrators to select a specific status and
 * displays all appointments that match the chosen status.
 * </p>
 */
public class ViewAppointmentsByStatusAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Constructs a {@code ViewAppointmentsByStatusAction} instance.
     *
     * @param appointmentController the controller used for interacting with appointment data
     */
    public ViewAppointmentsByStatusAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to view appointments by their status.
     * <p>
     * Displays a menu for selecting an appointment status, retrieves the
     * appointments with the selected status, and shows their details.
     * </p>
     */
    @Override
    public void execute() {
        UIUtils.displayHeader("Appointments by Status");

        System.out.println("Select status to view:");
        System.out.println("1. Requested");
        System.out.println("2. Confirmed");
        System.out.println("3. Cancelled");
        System.out.println("4. Completed");

        int choice = UIUtils.promptForInt("Enter choice", 1, 4);

        Appointment.AppointmentStatus status = switch (choice) {
            case 1 -> Appointment.AppointmentStatus.REQUESTED;
            case 2 -> Appointment.AppointmentStatus.CONFIRMED;
            case 3 -> Appointment.AppointmentStatus.CANCELLED;
            case 4 -> Appointment.AppointmentStatus.COMPLETED;
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        };

        List<Appointment> appointments = appointmentController.getAppointmentsByStatus(status);

        if (appointments.isEmpty()) {
            UIUtils.displayError("No appointments found with status: " + status);
            return;
        }

        System.out.printf("\nAppointments with %s Status:%n", status);
        displayAppointmentsDetailed(appointments);
        UIUtils.pressEnterToContinue();
    }

    /**
     * Displays a detailed list of appointments.
     * <p>
     * Each appointment's ID, patient ID, doctor ID, date & time, type,
     * and status are shown in a formatted manner.
     * </p>
     *
     * @param appointments the list of appointments to display
     */
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
