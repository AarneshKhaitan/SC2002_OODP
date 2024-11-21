package action.DoctorActions;

import util.UIUtils;
import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import entity.Appointments.Appointment;
import entity.users.User;

import java.util.List;

/**
 * Represents an action to manage pending appointment requests for a doctor.
 * 
 * <p>
 * This class allows doctors to view, accept, or decline appointment requests.
 * It interacts with the {@link DoctorAppointmentControllerImpl} to retrieve and handle requests,
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */

public class ManageAppointmentsAction implements DoctorAction {
    /**
     * Controller for managing doctor appointments.
     */
    private final DoctorAppointmentControllerImpl appointmentController;

    /**
     * Constructs an instance of {@code ManageAppointmentsAction}.
     * Initializes the appointment controller to handle appointment requests.
     */
    public ManageAppointmentsAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

     /**
     * Executes the action to manage appointment requests.
     * 
     * <p>
     * Displays all pending appointment requests for the doctor, allowing them to:
     * <ul>
     *   <li>View the details of each request</li>
     *   <li>Choose to accept or decline the request</li>
     *   <li>Update the status of the request in the system</li>
     * </ul>
     * </p>
     * 
     * <p>
     * Prompts the doctor for each request, processes the decision using the
     * {@link DoctorAppointmentControllerImpl}, and displays the outcome of the operation.
     * </p>
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Pending Appointment Requests");
        
         // Retrieve all pending appointment requests for the doctor
        List<Appointment> requests = appointmentController.getAppointmentRequests(doctor.getUserId());

        if (requests.isEmpty()) {
            UIUtils.displayError("No pending appointment requests.");
            return;
        }

         // Iterate through each request and process it
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
            
            // Prompt the doctor to accept or decline the request
            boolean accept = UIUtils.promptForYesNo("Accept this appointment request?");
            boolean success = appointmentController.handleAppointmentRequest(request.getAppointmentId(), accept);

             // Display the result of the operation
            if (success) {
                UIUtils.displaySuccess("Request " + (accept ? "accepted" : "declined") + ".");
            } else {
                UIUtils.displayError("Failed to update request status.");
            }
        }
        
         // Pause to allow the user to review the output
        UIUtils.pressEnterToContinue();
    }
}
