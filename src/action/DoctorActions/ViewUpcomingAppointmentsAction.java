package action.DoctorActions;

import util.UIUtils;
import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import entity.Appointments.Appointment;
import entity.users.User;

import java.util.List;

/**
 * Represents an action to view a doctor's upcoming appointments.
 * 
 * This class retrieves and displays a list of upcoming appointments for the doctor,
 * helping them prepare for patient interactions. The class interacts with the 
 * {@link DoctorAppointmentControllerImpl} to fetch the appointment data and uses
 * {@link UIUtils} for user interaction in the console-based UI.
 */
public class ViewUpcomingAppointmentsAction implements DoctorAction {
    
    /**
     * Controller for managing doctor appointments.
     */
    private final DoctorAppointmentControllerImpl appointmentController;

     /**
     * Constructs an instance of {@code ViewUpcomingAppointmentsAction}.
     * Initializes the appointment controller to retrieve the doctor's upcoming appointments.
     */
    public ViewUpcomingAppointmentsAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    /**
     * Executes the action to display the doctor's upcoming appointments.
     * 
     * The steps include:
     * <ul>
     *   <li>Retrieving a list of upcoming appointments for the doctor.</li>
     *   <li>Checking if there are any upcoming appointments.</li>
     *   <li>Displaying the details of the appointments in a formatted structure.</li>
     * </ul>
     *
     * If there are no upcoming appointments, an error message is displayed.
     *
     * @param doctor The {@link User} object representing the doctor viewing their upcoming appointments.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Upcoming Appointments");

        // Retrieve upcoming appointments for the doctor
        List<Appointment> appointments = appointmentController.getDoctorUpcomingAppointments(doctor.getUserId());

        if (appointments.isEmpty()) {
            UIUtils.displayError("No upcoming appointments.");
            return;
        }

        // Display upcoming appointments
        System.out.println("\nYour upcoming schedule:");
        appointments.forEach(appointment -> System.out.printf("""
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
                appointment.getStatus()));
        
         // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
