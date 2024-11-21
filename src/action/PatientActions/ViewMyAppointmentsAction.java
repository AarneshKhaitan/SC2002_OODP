package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to view all upcoming appointments for a patient.
 * 
 * This class provides patients with a clear overview of their scheduled appointments,
 * allowing for better management and preparation. It interacts with the
 * {@link PatientAppointmentController} to retrieve the list of appointments and uses
 * {@link AppointmentDisplayUtil} to format and display the appointments in a user-friendly way.
 */
public class ViewMyAppointmentsAction implements PatientAction {

     /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code ViewMyAppointmentsAction}.
     * 
     * @param appointmentController The controller used to manage and retrieve patient appointments.
     */
    public ViewMyAppointmentsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to display all upcoming appointments for the patient.
     * 
     * The steps include:
     * <ul>
     *   <li>Retrieving the list of appointments for the patient.</li>
     *   <li>Checking if there are any scheduled appointments.</li>
     *   <li>Displaying the appointments in a formatted structure.</li>
     * </ul>
     *
     * If the patient has no scheduled appointments, an error message is displayed.
     *
     * @param patient The {@link User} object representing the patient viewing their appointments.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("My Appointments");

        // Retrieve the patient's scheduled appointments
        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            UIUtils.displayError("You have no appointments scheduled.");
            return;
        }

          // Display the appointments
        AppointmentDisplayUtil.displayAppointments(appointments);

        // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
