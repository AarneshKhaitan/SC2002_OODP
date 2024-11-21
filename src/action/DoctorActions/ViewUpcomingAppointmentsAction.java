package action.DoctorActions;

import util.UIUtils;
import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
import entity.Appointments.Appointment;
import entity.users.User;

import java.util.List;

public class ViewUpcomingAppointmentsAction implements DoctorAction {
    private final DoctorAppointmentControllerImpl appointmentController;

    public ViewUpcomingAppointmentsAction() {
        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
    }

    @Override
    public void execute(User doctor) {
        UIUtils.displayHeader("Upcoming Appointments");
        List<Appointment> appointments = appointmentController.getDoctorUpcomingAppointments(doctor.getUserId());

        if (appointments.isEmpty()) {
            UIUtils.displayError("No upcoming appointments.");
            return;
        }

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
        UIUtils.pressEnterToContinue();
    }
}