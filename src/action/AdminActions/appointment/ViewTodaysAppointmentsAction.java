package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import util.UIUtils;

import java.util.List;

/**
 * Provides functionality to view all appointments scheduled for today.
 *
 * This action retrieves and displays a list of appointments that are scheduled
 * for the current date. It includes details such as appointment ID, patient ID,
 * doctor ID, date and time, type, and status.
 */
public class ViewTodaysAppointmentsAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Constructs a {@code ViewTodaysAppointmentsAction} instance.
     *
     * @param appointmentController the controller used to retrieve today's appointments
     */
    public ViewTodaysAppointmentsAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to view today's appointments.
     *
     * Retrieves the list of appointments scheduled for today and displays their details.
     * If no appointments are found, an error message is displayed.
     */
    @Override
    public void execute() {
        UIUtils.displayHeader("Today's Appointments");
        List<Appointment> appointments = appointmentController.getTodaysAppointments();

        if (appointments.isEmpty()) {
            UIUtils.displayError("No appointments scheduled for today.");
            return;
        }

        System.out.println("\nToday's Schedule:");
        displayAppointmentsDetailed(appointments);
        UIUtils.pressEnterToContinue();
    }

    /**
     * Displays detailed information for a list of today's appointments.
     *
     * Each appointment's ID, patient ID, doctor ID, date & time, type,
     * and status are displayed in a formatted output.
     *
     * @param appointments the list of appointments scheduled for today
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
                    appointment.getStatus()
            );
        }
    }
}
