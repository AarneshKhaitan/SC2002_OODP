package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import util.UIUtils;

import java.util.List;

/**
 * Provides functionality to view all current appointments in real time.
 * This action retrieves all appointments from the system and displays their details,
 * including appointment ID, patient ID, doctor ID, date and time, type, and status.
 */
public class ViewRealTimeAppointmentsAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Constructs a {@code ViewRealTimeAppointmentsAction} instance.
     *
     * @param appointmentController the controller used for retrieving appointment data
     */
    public ViewRealTimeAppointmentsAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to view real-time updates of all appointments.
     *
     * Retrieves the list of all appointments and displays their details in a formatted manner.
     * If no appointments are found, an error message is displayed.
     */
    @Override
    public void execute() {
        UIUtils.displayHeader("Real-time Appointment Updates");
        List<Appointment> appointments = appointmentController.getAllAppointments();

        if (appointments.isEmpty()) {
            UIUtils.displayError("No appointments found in the system.");
            return;
        }

        System.out.println("\nAll Current Appointments:");
        displayAppointmentsDetailed(appointments);
        UIUtils.pressEnterToContinue();
    }

    /**
     * Displays detailed information for a list of appointments.
     *
     * Each appointment's ID, patient ID, doctor ID, date & time, type,
     * and status are displayed in a formatted output.
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
