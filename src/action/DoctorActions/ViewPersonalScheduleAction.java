package action.DoctorActions;

import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.UIUtils;

import java.util.List;

/**
 * Represents an action to view a doctor's personal schedule.
 * 
 * <p>
 * This class allows doctors to view their upcoming appointments, meetings, and other commitments.
 * Appointments are grouped by their status (e.g., Confirmed, Completed, or Cancelled) to provide
 * an organized and detailed view of the schedule.
 * </p>
 * 
 * <p>
 * The class interacts with the {@link DoctorAppointmentController} to retrieve appointment details
 * and uses {@link UIUtils} for user interaction in the console-based UI.
 * </p>
 */
public class ViewPersonalScheduleAction implements DoctorAction {

    /**
     * Controller for managing doctor appointments.
     */
    private final DoctorAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code ViewPersonalScheduleAction}.
     * Initializes the appointment controller to retrieve the doctor's schedule.
     */
    public ViewPersonalScheduleAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

        /**
     * Executes the action to display the doctor's personal schedule.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving the list of appointments for the doctor.</li>
     *   <li>Checking if there are any appointments scheduled.</li>
     *   <li>Grouping the appointments by their status (e.g., Confirmed, Completed).</li>
     *   <li>Displaying the grouped appointments in an organized format.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If there are no scheduled appointments, an error message is displayed.
     * </p>
     * 
     * @param doctor The {@link User} object representing the doctor viewing their schedule.
     */
    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Personal Schedule");

          // Retrieve all appointments for the doctor
        List<Appointment> appointments = appointmentController.getDoctorAppointments(doctor.getUserId());

        if (appointments.isEmpty()) {
            UIUtils.displayError("No appointments scheduled.");
            return;
        }

        // Group appointments by status
        System.out.println("\nYour Current Schedule:");
        for (Appointment.AppointmentStatus status : Appointment.AppointmentStatus.values()) {
            List<Appointment> statusAppointments = appointments.stream()
                    .filter(a -> a.getStatus() == status)
                    .toList();

            if (!statusAppointments.isEmpty()) {
                System.out.printf("\n%s Appointments:%n", status);
                displayAppointments(statusAppointments);
            }
        }
        UIUtils.pressEnterToContinue();
    }

      /**
     * Displays the list of appointments in a formatted structure.
     * 
     * @param appointments The list of {@link Appointment} objects to display.
     */
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
