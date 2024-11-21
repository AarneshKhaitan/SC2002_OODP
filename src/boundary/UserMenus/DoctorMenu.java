
package boundary.UserMenus;

import action.DoctorActions.*;
import boundary.Menu;
import util.UIUtils;
import entity.users.User;

import java.util.Map;
/**
 * Represents the menu interface for a Doctor user.
 * Provides options for managing patient records, personal schedule, and appointments.
 */
public class DoctorMenu implements Menu {
    private final User doctor;
    private final Map<Integer, DoctorAction> actions;

    /**
     * Constructs a DoctorMenu instance.
     *
     * @param doctor the doctor user for whom the menu is being displayed
     */

    public DoctorMenu(User doctor) {
        // Initializes the doctor menu with the specified doctor.
        this.doctor = doctor;

        this.actions = Map.of(
                1, new ViewPatientRecordsAction(),
                2, new UpdatePatientRecordsAction(),
                3, new ViewPersonalScheduleAction(),
                4, new SetAvailabilityAction(),
                5, new ManageAppointmentsAction(),
                6, new ViewUpcomingAppointmentsAction(),
                7, new UpdateAppointmentStatusAction()
        );
    }
    /**
     * Displays the Doctor menu with options for various operations such as viewing/updating patient records,
     * managing schedules, and handling appointments.
     *
     * @param user the doctor user
     */

    @Override
    public void display(User user) {
        // Displays menu options and executes the selected action.
        while (true) {
            UIUtils.displayHeader("Doctor Menu");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept/Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Update Appointment Status");
            System.out.println("8. Logout");

            int choice = UIUtils.promptForInt("Choose an option", 1, 8);

            if (choice == 8) {
                UIUtils.displaySuccess("Logging out...");
                return;
            }

            // Retrieve and execute the corresponding action
            DoctorAction action = actions.get(choice);
            if (action != null) {
                action.execute(doctor);
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
