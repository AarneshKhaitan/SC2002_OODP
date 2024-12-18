package action.AdminActions.appointment;

import action.AdminActions.AdminAction;
import controller.AppointmentControllers.AdminAppointmentControllerImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles the appointment management functionality for administrators.
 * <p>
 * This class provides a menu-driven interface to manage appointments,
 * allowing administrators to perform actions such as viewing appointments
 * by status, managing appointment statuses, and viewing statistics.
 * </p>
 */
public class AppointmentManagerAction implements AdminAction {
    private final Map<Integer, AdminAction> actions;
    private final AdminAppointmentControllerImpl appointmentController;

    /**
     * Initializes the {@code AppointmentManagerAction} and sets up the
     * available actions for appointment management.
     */
    public AppointmentManagerAction() {
        this.appointmentController = AdminAppointmentControllerImpl.getInstance();
        actions = new HashMap<>();
        actions.put(1, new ViewRealTimeAppointmentsAction(appointmentController));
        actions.put(2, new ViewAppointmentsByStatusAction(appointmentController));
        actions.put(3, new ManageAppointmentStatusAction(appointmentController));
        actions.put(4, new ViewTodaysAppointmentsAction(appointmentController));
        actions.put(5, new ViewAppointmentStatisticsAction(appointmentController));
        actions.put(6, new ViewAppointmentOutcomesAction(appointmentController));
    }

    /**
     * Displays the appointment management menu and handles user input to
     * execute the selected action.
     * <p>
     * The method provides options for various administrative tasks related to
     * appointment management. It loops until the user chooses to return
     * to the main menu.
     * </p>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Appointment Management ===");
            System.out.println("1. View Real-time Appointments");
            System.out.println("2. View Appointments by Status");
            System.out.println("3. Manage Appointment Status");
            System.out.println("4. View Today's Appointments");
            System.out.println("5. View Appointment Statistics");
            System.out.println("6. View Appointment Outcomes");
            System.out.println("7. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 7) {
                System.out.println("Returning to main menu...");
                return;
            }

            AdminAction action = actions.get(choice);
            if (action != null) {
                action.execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
