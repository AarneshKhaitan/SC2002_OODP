package action.AdminActions.appointment;

import controller.AppointmentControllers.AdminAppointmentControllerImpl;
import entity.Appointments.Appointment;
import entity.Appointments.AppointmentOutcomeRecord;
import util.AppointmentOutcomeManager;
import util.UIUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import action.AdminActions.AdminAction;


public class ViewAppointmentOutcomesAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    public ViewAppointmentOutcomesAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    @Override
    public void execute() {
        UIUtils.displayHeader("View Appointment Outcomes");

        System.out.println("1. View by Patient ID");
        System.out.println("2. View Recent Outcomes");
        System.out.println("3. Back to Reports Menu");

        int choice = UIUtils.promptForInt("Choose an option", 1, 3);

        switch (choice) {
            case 1 -> viewByPatientId();
            case 2 -> viewRecentOutcomes();
            case 3 -> System.out.println("Returning to main menu...");
        }
    }

    private void viewByPatientId() {
        String patientId = UIUtils.promptForString("Enter Patient ID");
        List<AppointmentOutcomeRecord> outcomes =
                AppointmentOutcomeManager.getInstance().getPatientOutcomeRecords(patientId);
        displayOutcomeRecords(outcomes);
    }

    private void viewRecentOutcomes() {
        List<Appointment> recentAppointments =
                appointmentController.getAppointmentsByStatus(Appointment.AppointmentStatus.COMPLETED);
        List<AppointmentOutcomeRecord> outcomes = recentAppointments.stream()
                .map(apt -> AppointmentOutcomeManager.getInstance()
                        .getOutcomeRecord(apt.getAppointmentId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        displayOutcomeRecords(outcomes);
    }

    private void displayOutcomeRecords(List<AppointmentOutcomeRecord> outcomes) {
        if (outcomes.isEmpty()) {
            UIUtils.displayError("No outcome records found.");
            return;
        }

        for (AppointmentOutcomeRecord outcome : outcomes) {
            System.out.printf("""
            ========================================
            Appointment ID: %s
            Patient ID: %s
            Doctor ID: %s
            Date: %s
            Type: %s
            
            Consultation Notes:
            %s
            
            Prescriptions:
            """,
                    outcome.getAppointmentId(),
                    outcome.getPatientId(),
                    outcome.getDoctorId(),
                    UIUtils.formatDateTime(outcome.getAppointmentDate()),
                    outcome.getAppointmentType(),
                    outcome.getConsultationNotes()
            );

            for (var prescription : outcome.getPrescriptions()) {
                System.out.printf("- %s (Status: %s)%n",
                        prescription.getMedicationName(),
                        prescription.getStatus().getDisplayName()
                );
            }
            System.out.println("========================================\n");
        }
        UIUtils.pressEnterToContinue();
    }
}
