package action.DoctorActions;

import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import controller.interfaces.DoctorAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.UIUtils;

import java.util.List;

public class ViewPersonalScheduleAction implements DoctorAction {
    private final DoctorAppointmentController appointmentController;

    public ViewPersonalScheduleAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Personal Schedule");
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
