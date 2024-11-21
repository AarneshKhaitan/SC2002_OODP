package action.DoctorActions;

import util.UIUtils;
import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import entity.Appointments.Appointment;
import entity.users.User;

import java.util.List;

/**
 * Class responsible for managing appointments.
 * Includes functionality for creating new appointments, updating existing ones, and canceling if necessary.
 */
public class ManageAppointmentsAction implements DoctorAction {
    private final DoctorAppointmentControllerImpl appointmentController;

    public ManageAppointmentsAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Pending Appointment Requests");
        List<Appointment> requests = appointmentController.getAppointmentRequests(doctor.getUserId());

        if (requests.isEmpty()) {
            UIUtils.displayError("No pending appointment requests.");
            return;
        }

        for (Appointment request : requests) {
            System.out.printf("""
                ----------------------------------------
                Request ID: %s
                Patient ID: %s
                Date & Time: %s
                Type: %s
                ----------------------------------------
                """,
                    request.getAppointmentId(),
                    request.getPatientId(),
                    UIUtils.formatDateTime(request.getDateTime()),
                    request.getType());

            boolean accept = UIUtils.promptForYesNo("Accept this appointment request?");
            boolean success = appointmentController.handleAppointmentRequest(request.getAppointmentId(), accept);

            if (success) {
                UIUtils.displaySuccess("Request " + (accept ? "accepted" : "declined") + ".");
            } else {
                UIUtils.displayError("Failed to update request status.");
            }
        }
        UIUtils.pressEnterToContinue();
    }
}
