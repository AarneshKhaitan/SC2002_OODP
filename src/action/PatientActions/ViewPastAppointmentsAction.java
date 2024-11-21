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

/**
 * Represents an action to view a patient's past appointments and their outcomes.
 * 
 * <p>
 * This class allows patients to access historical data about their completed appointments,
 * including consultation notes and prescribed medications. It interacts with the 
 * {@link PatientAppointmentController} to retrieve past appointments and uses the 
 * {@link AppointmentOutcomeManager} to fetch associated outcome records.
 * </p>
 */
public class ViewPastAppointmentsAction implements PatientAction {

    /**
     * Controller for managing patient appointments.
     */
    private final PatientAppointmentController appointmentController;

    /**
     * Constructs an instance of {@code ViewPastAppointmentsAction}.
     * 
     * @param appointmentController The controller used to manage and retrieve patient appointments.
     */
    public ViewPastAppointmentsAction(PatientAppointmentController appointmentController) {
        this.appointmentController = appointmentController;
    }

     /**
     * Executes the action to display a patient's past appointments and their outcomes.
     * 
     * <p>
     * The steps include:
     * <ul>
     *   <li>Retrieving the list of the patient's appointments.</li>
     *   <li>Filtering the appointments to include only completed ones.</li>
     *   <li>Displaying the past appointments in a formatted structure.</li>
     *   <li>Displaying the outcomes for each appointment, including consultation notes and prescriptions.</li>
     * </ul>
     * </p>
     * 
     * <p>
     * If no past appointments are found, an error message is displayed.
     * </p>
     * 
     * @param patient The {@link User} object representing the patient viewing their past appointments.
     */
    @Override
    public void execute(User patient) {
        UIUtils.displayHeader("Past Appointments and Outcomes");

         // Retrieve the patient's appointments
        List<Appointment> appointments = appointmentController.getPatientAppointments(patient.getUserId());

         // Filter for completed appointments
        List<Appointment> pastAppointments = appointments.stream()
                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.COMPLETED)
                .toList();

        if (pastAppointments.isEmpty()) {
            UIUtils.displayError("No past appointments found.");
            return;
        }

        // Display the past appointments
        AppointmentDisplayUtil.displayAppointments(pastAppointments);

        // Display outcomes for each past appointment
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

          // Pause for user review
        UIUtils.pressEnterToContinue();
    }
}
