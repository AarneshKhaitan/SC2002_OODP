package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import util.UIUtils;

import java.util.Map;

public class ViewAppointmentStatisticsAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    public ViewAppointmentStatisticsAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute() {
        UIUtils.displayHeader("Appointment Statistics");
        Map<String, Integer> stats = appointmentController.getAppointmentStatistics();

        System.out.println("\nOverall Statistics:");
        System.out.printf("Total Appointments: %d%n", stats.get("total"));

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
                    System.out.printf("Doctor %s: %d appointments%n",
                            doctorId, e.getValue());
                });

        UIUtils.pressEnterToContinue();
    }
}
