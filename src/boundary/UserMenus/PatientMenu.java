
package boundary.UserMenus;

import action.PatientActions.*;
import boundary.Menu;
import controller.MedicalRecords.MedicalRecordController;
import controller.AppointmentControllers.PatientAppointmentControllerImpl;
import entity.users.User;
import util.UIUtils;

import java.util.HashMap;
import java.util.Map;
/**
 * Represents the menu interface for a Patient user.
 * Provides options for managing medical records, appointments, and personal information.
 */
public class PatientMenu implements Menu {
    private final Map<Integer, PatientAction> actions = new HashMap<>();
     /**
     * Constructs a PatientMenu instance.
     *
     * @param patient the patient user for whom the menu is being displayed
     */
    public PatientMenu(User patient) {
        // Initialize actions with corresponding functionality
        actions.put(1, new ViewMedicalRecordAction(MedicalRecordController.getInstance()));
        actions.put(2, new UpdatePersonalInfoAction(MedicalRecordController.getInstance()));
        actions.put(3, new ViewAvailableSlotsAction(PatientAppointmentControllerImpl.getInstance()));
        actions.put(4, new ScheduleAppointmentAction(PatientAppointmentControllerImpl.getInstance()));
        actions.put(5, new ViewMyAppointmentsAction(PatientAppointmentControllerImpl.getInstance()));
        actions.put(6, new CancelAppointmentAction(PatientAppointmentControllerImpl.getInstance()));
        actions.put(7, new RescheduleAppointmentAction(PatientAppointmentControllerImpl.getInstance()));
        actions.put(8, new ViewPastAppointmentsAction(PatientAppointmentControllerImpl.getInstance()));
    }
     /**
     * Displays the Patient menu with options for various operations such as viewing/updating medical records,
     * scheduling/cancelling appointments, and viewing past records.
     *
     * @param user the patient user
     */
    @Override
    public void display(User user) {
        // Displays menu options and executes the selected action.
        while (true) {
            UIUtils.displayHeader("Patient Menu");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. View My Appointments");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Reschedule Appointment");
            System.out.println("8. View Past Appointment Records");
            System.out.println("9. Logout");

            int choice = UIUtils.promptForInt("Choose an option", 1, 9);

            if (choice == 9) {
                UIUtils.displaySuccess("Logging out...");
                return;
            }

            PatientAction action = actions.get(choice);
            if (action != null) {
                action.execute(user);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

