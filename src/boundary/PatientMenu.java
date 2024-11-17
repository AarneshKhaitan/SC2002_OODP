package boundary;

import entity.User;
import java.util.Scanner;

public class PatientMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display(User user) {
        while (true) {
            System.out.println("\n=== Patient Menu ===");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. View Scheduled Appointments");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewMedicalRecord();
                case "2" -> updatePersonalInfo();
                case "3" -> viewAvailableSlots();
                case "4" -> scheduleAppointment();
                case "5" -> viewScheduledAppointments();
                case "6" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewMedicalRecord() {
        System.out.println("Viewing medical record... [To be implemented]");
    }

    private void updatePersonalInfo() {
        System.out.println("Updating personal information... [To be implemented]");
    }

    private void viewAvailableSlots() {
        System.out.println("Viewing available appointment slots... [To be implemented]");
    }

    private void scheduleAppointment() {
        System.out.println("Scheduling appointment... [To be implemented]");
    }

    private void viewScheduledAppointments() {
        System.out.println("Viewing scheduled appointments... [To be implemented]");
    }
}
