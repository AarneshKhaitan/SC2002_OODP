package action.PatientActions;

import controller.interfaces.PatientAppointmentController;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentOutcomeRecord;
import entity.Medications.Prescription;
import entity.users.User;
import util.AppointmentDisplayUtil;
import util.AppointmentOutcomeManager;
import util.UIUtils;

import java.util.List;

public class ViewPastAppointmentsAction implements PatientAction {
    private final PatientAppointmentController appointmentController;

    public ViewPastAppointmentsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Past Appointments and Outcomes");

        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());
        List<Appointment> pastAppointments = appointments.stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.COMPLETED)
                .toList();

        if (pastAppointments.isEmpty()) {
            UIUtils.displayError("No past appointments found.");
            return;
        }

        AppointmentDisplayUtil.displayAppointments(pastAppointments);

        System.out.println("\nOutcome Records:");
        for (Appointment appointment : pastAppointments) {
            AppointmentOutcomeRecord outcome = AppointmentOutcomeManager.getInstance().getOutcomeRecord(appointment.getAppointmentId());

            if (outcome != null) {
                System.out.printf("""
                                ----------------------------------------
                                Appointment ID: %s
                                Consultation Notes: %s
                                
                                Prescribed Medications:
                                """,
                        outcome.getAppointmentId(),
                        outcome.getConsultationNotes());

                for (Prescription prescription : outcome.getPrescriptions()) {
                    System.out.printf("- %s (Status: %s)%n",
                            prescription.getMedicationName(),
                            prescription.getStatus().getDisplayName());
                }
                System.out.println("----------------------------------------");
            }
        }

        UIUtils.pressEnterToContinue();
    }
}
