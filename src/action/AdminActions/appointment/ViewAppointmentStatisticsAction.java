package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import util.UIUtils;

import java.util.Map;

/**
 * Provides functionality to view statistical data about appointments.
 * <p>
 * This action retrieves and displays statistics such as the total number of appointments,
 * counts by status (e.g., requested, confirmed, completed, cancelled), and appointment
 * counts by doctor.
 * </p>
 */
public class ViewAppointmentStatisticsAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Constructs a {@code ViewAppointmentStatisticsAction} instance.
     *
     * @param appointmentController the controller used for retrieving appointment statistics
     */
    public ViewAppointmentStatisticsAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to view appointment statistics.
     * <p>
     * Displays the overall total number of appointments, counts by status, and
     * appointments categorized by doctor. This information is retrieved from
     * the appointment controller.
     * </p>
     */
    @Override
    public void execute() {
        UIUtils.displayHeader("Appointment Statistics");
        Map<String, Integer> stats = appointmentController.getAppointmentStatistics();

        System.out.println("\nOverall Statistics:");
        System.out.printf("Total Appointments: %d%n", stats.getOrDefault("total", 0));

        System.out.println("\nBy Status:");
        System.out.printf("Requested: %d%n", stats.getOrDefault("requested", 0));
        System.out.printf("Confirmed: %d%n", stats.getOrDefault("confirmed", 0));
        System.out.printf("Completed: %d%n", stats.getOrDefault("completed", 0));
        System.out.printf("Cancelled: %d%n", stats.getOrDefault("cancelled", 0));

        System.out.println("\nBy Doctor:");
        stats.entrySet().stream()
                .filter(e -> e.getKey().startsWith("doctor_"))
                .forEach(e -> {
                    String doctorId = e.getKey().substring(7);
                    System.out.printf("Doctor %s: %d appointments%n", doctorId, e.getValue());
                });

        UIUtils.pressEnterToContinue();
    }
}
