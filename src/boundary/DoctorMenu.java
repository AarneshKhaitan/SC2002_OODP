package boundary;

import entity.User;
import java.util.Scanner;

public class DoctorMenu implements Menu {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void display(User user) {
        while (true) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability");
            System.out.println("5. View Upcoming Appointments");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewPatientRecords();
                case "2" -> updatePatientRecords();
                case "3" -> viewSchedule();
                case "4" -> setAvailability();
                case "5" -> viewAppointments();
                case "6" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewPatientRecords() {
        System.out.println("Viewing patient records... [To be implemented]");
    }

    private void updatePatientRecords() {
        System.out.println("Updating patient records... [To be implemented]");
    }

    private void viewSchedule() {
        System.out.println("Viewing schedule... [To be implemented]");
    }

    private void setAvailability() {
        System.out.println("Setting availability... [To be implemented]");
    }

    private void viewAppointments() {
        System.out.println("Viewing appointments... [To be implemented]");
    }
}
