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

/**
 * Allows administrators to view appointment outcomes.
 * <p>
 * This action provides options to view appointment outcomes by patient ID
 * or to view recent outcomes for completed appointments. It displays
 * detailed information, including consultation notes and prescriptions.
 * </p>
 */
public class ViewAppointmentOutcomesAction implements AdminAction {
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Constructs a {@code ViewAppointmentOutcomesAction} instance.
     *
     * @param appointmentController the controller used for interacting with appointment data
     */
    public ViewAppointmentOutcomesAction(AdminAppointmentControllerImpl appointmentController) {
        this.appointmentController = appointmentController;
    }

    /**
     * Executes the action to view appointment outcomes.
     * <p>
     * Displays a menu for the administrator to choose between viewing outcomes
     * by patient ID or viewing recent outcomes. Each option provides detailed
     * information about the selected outcomes.
     * </p>
     */
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

    /**
     * Displays appointment outcomes for a specific patient by their ID.
     * <p>
     * Prompts the administrator to enter the patient ID, retrieves the
     * associated outcome records, and displays detailed information.
     * </p>
     */
    private void viewByPatientId() {
        String patientId = UIUtils.promptForString("Enter Patient ID");
        List<AppointmentOutcomeRecord> outcomes =
                AppointmentOutcomeManager.getInstance().getPatientOutcomeRecords(patientId);
        displayOutcomeRecords(outcomes);
    }

    /**
     * Displays recent appointment outcomes for completed appointments.
     * <p>
     * Retrieves completed appointments, fetches their associated outcome records,
     * and displays the details.
     * </p>
     */
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

    /**
     * Displays a detailed list of appointment outcome records.
     * <p>
     * Includes information such as consultation notes and prescriptions.
     * If no records are found, an error message is displayed.
     * </p>
     *
     * @param outcomes the list of outcome records to display
     */
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
