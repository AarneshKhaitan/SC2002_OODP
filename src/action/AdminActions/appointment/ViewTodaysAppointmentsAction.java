package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import util.UIUtils;

import java.util.List;

public class ViewTodaysAppointmentsAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    public ViewTodaysAppointmentsAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

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
