package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.UIUtils;

import java.util.List;

public class ViewMyAppointmentsAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public ViewMyAppointmentsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("My Appointments");

        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());
        if (appointments.isEmpty()) {
            UIUtils.displayError("You have no appointments scheduled.");
            return;
        }

        AppointmentDisplayUtil.displayAppointments(appointments);

        UIUtils.pressEnterToContinue();
    }
}
