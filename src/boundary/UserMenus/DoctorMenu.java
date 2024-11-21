//package boundary.UserMenus;
//
//import boundary.Menu;
//import util.UIUtils;
//import controller.interfaces.DoctorAppointmentController;
//import controller.MedicalRecords.MedicalRecordController;
//import controller.AppointmentControllers.DoctorAppointmentControllerImpl;
//import entity.Appointments.Appointment;
//import entity.Appointments.AppointmentOutcomeRecord;
//import entity.Records.Diagnosis;
//import entity.Records.MedicalRecord;
//import entity.Records.Treatment;
//import entity.users.User;
//import util.AppointmentOutcomeManager;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class DoctorMenu implements Menu {
//    private final Scanner scanner = new Scanner(System.in);
//    private final DoctorAppointmentController appointmentController;
//    private final MedicalRecordController medicalRecordController;
//    private final User doctor;
//
//    public DoctorMenu(User doctor) {
//        this.doctor = doctor;
//        this.appointmentController = DoctorAppointmentControllerImpl.getInstance();
//        this.medicalRecordController = MedicalRecordController.getInstance();
//    }
//
//    @Override
//    public void display(User user) {
//        while (true) {
//            UIUtils.displayHeader("Doctor Menu");
//            System.out.println("1. View Patient Medical Records");
//            System.out.println("2. Update Patient Medical Records");
//            System.out.println("3. View Personal Schedule");
//            System.out.println("4. Set Availability for Appointments");
//            System.out.println("5. Accept/Decline Appointment Requests");
//            System.out.println("6. View Upcoming Appointments");
//            System.out.println("7. Update Appointment Status");
//
//            System.out.println("8. Logout");
//
//            int choice = UIUtils.promptForInt("Choose an option", 1, 9);
//
//            switch (choice) {
//                case 1 -> viewPatientRecords();
//                case 2 -> updatePatientRecords();
//                case 3 -> viewPersonalSchedule();
//                case 4 -> setAvailability();
//                case 5 -> manageAppointmentRequests();
//                case 6 -> viewUpcomingAppointments();
//                case 7 -> updateAppointmentStatus();
//
//                case 8 -> {
//                    UIUtils.displaySuccess("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }
//
//    // New Medical Record Methods
//    private void viewPatientRecords() {
//        UIUtils.displayHeader("View Patient Medical Records");
//        String patientId = UIUtils.promptForString("Enter Patient ID");
//
//        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patientId);
//        if (record == null) {
//            UIUtils.displayError("Medical record not found.");
//            return;
//        }
//
//        // Display patient information
//        System.out.println("\nPatient Information:");
//        System.out.printf("Patient ID: %s%n", record.getPatientId());
//        System.out.printf("Name: %s%n", record.getName());
//        System.out.printf("Date of Birth: %s%n", record.getDateOfBirth());
//        System.out.printf("Gender: %s%n", record.getGender());
//        System.out.printf("Blood Type: %s%n", record.getBloodType());
//        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
//        System.out.printf("Email: %s%n", record.getEmailAddress());
//
//        // Display diagnoses
//        System.out.println("\nDiagnoses History:");
//        List<Diagnosis> diagnoses = record.getDiagnoses();
//        if (diagnoses.isEmpty()) {
//            System.out.println("No diagnoses recorded.");
//        } else {
//            diagnoses.forEach(diagnosis -> System.out.printf("""
//                Date: %s
//                Doctor: %s
//                Condition: %s
//                Notes: %s
//                ----------------------------------------
//                """,
//                    diagnosis.getDate(),
//                    diagnosis.getDoctorId(),
//                    diagnosis.getCondition(),
//                    diagnosis.getNotes()
//            ));
//        }
//
//        // Display treatments
//        System.out.println("\nTreatments History:");
//        List<Treatment> treatments = record.getTreatments();
//        if (treatments.isEmpty()) {
//            System.out.println("No treatments recorded.");
//        } else {
//            treatments.forEach(treatment -> System.out.printf("""
//                Date: %s
//                Doctor: %s
//                Type: %s
//                Medications: %s
//                Instructions: %s
//                ----------------------------------------
//                """,
//                    treatment.getDate(),
//                    treatment.getDoctorId(),
//                    treatment.getTreatmentType(),
//                    String.join(", ", treatment.getMedications()),
//                    treatment.getInstructions()
//            ));
//        }
//
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void updatePatientRecords() {
//        UIUtils.displayHeader("Update Patient Medical Records");
//        String patientId = UIUtils.promptForString("Enter Patient ID");
//
//        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(patientId);
//        if (record == null) {
//            UIUtils.displayError("Medical record not found.");
//            return;
//        }
//
//        System.out.println("\nUpdate Options:");
//        System.out.println("1. Add New Diagnosis");
//        System.out.println("2. Add New Treatment");
//        System.out.println("3. Back to Main Menu");
//
//        int choice = UIUtils.promptForInt("Choose an option", 1, 3);
//
//        switch (choice) {
//            case 1 -> addNewDiagnosis(patientId);
//            case 2 -> addNewTreatment(patientId);
//        }
//    }
//
//    private void addNewDiagnosis(String patientId) {
//        UIUtils.displayHeader("Add New Diagnosis");
//
//        String condition = UIUtils.promptForString("Enter condition/diagnosis");
//        String notes = UIUtils.promptForString("Enter additional notes");
//
//        if (medicalRecordController.addDiagnosis(
//                patientId, doctor.getUserId(), condition, notes)) {
//            UIUtils.displaySuccess("Diagnosis added successfully!");
//        } else {
//            UIUtils.displayError("Failed to add diagnosis.");
//        }
//
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void addNewTreatment(String patientId) {
//        UIUtils.displayHeader("Add New Treatment");
//
//        String treatmentType = UIUtils.promptForString("Enter treatment type");
//
//        List<String> medications = new ArrayList<>();
//        while (true) {
//            String medication = UIUtils.promptForString(
//                    "Enter medication (or press Enter to finish)");
//            if (medication.isEmpty()) break;
//            medications.add(medication);
//        }
//
//        String instructions = UIUtils.promptForString("Enter treatment instructions");
//
//        if (medicalRecordController.addTreatment(
//                patientId, doctor.getUserId(), treatmentType, medications, instructions)) {
//            UIUtils.displaySuccess("Treatment added successfully!");
//        } else {
//            UIUtils.displayError("Failed to add treatment.");
//        }
//
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void viewPersonalSchedule() {
//        UIUtils.displayHeader("Personal Schedule");
//        List<Appointment> appointments =
//                appointmentController.getDoctorAppointments(doctor.getUserId());
//
//        if (appointments.isEmpty()) {
//            UIUtils.displayError("No appointments scheduled.");
//            return;
//        }
//
//        // Group appointments by status
//        System.out.println("\nCurrent Schedule:");
//        for (Appointment.AppointmentStatus status : Appointment.AppointmentStatus.values()) {
//            List<Appointment> statusAppointments = appointments.stream()
//                    .filter(a -> a.getStatus() == status)
//                    .toList();
//
//            if (!statusAppointments.isEmpty()) {
//                System.out.printf("\n%s Appointments:%n", status);
//                displayAppointments(statusAppointments);
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void setAvailability() {
//        UIUtils.displayHeader("Set Availability");
//        List<LocalDateTime> slots = new ArrayList<>();
//
//        System.out.println("\nEnter time slots for availability");
//        System.out.println("Format: yyyy-MM-dd HH:mm");
//        System.out.println("Enter 'done' when finished\n");
//
//        while (true) {
//            String input = UIUtils.promptForString("Enter slot datetime (or 'done' to finish)");
//
//            if (input.equalsIgnoreCase("done")) {
//                break;
//            }
//
//            try {
//                LocalDateTime dateTime = LocalDateTime.parse(input,
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//
//                if (dateTime.isBefore(LocalDateTime.now())) {
//                    UIUtils.displayError("Cannot add slots in the past.");
//                    continue;
//                }
//
//                slots.add(dateTime);
//                UIUtils.displaySuccess("Slot added successfully.");
//            } catch (Exception e) {
//                UIUtils.displayError("Invalid date format. Please use yyyy-MM-dd HH:mm");
//            }
//        }
//
//        if (!slots.isEmpty()) {
//            boolean success = appointmentController.addDoctorSlots(doctor.getUserId(), slots);
//            if (success) {
//                UIUtils.displaySuccess("Availability slots added successfully!");
//            } else {
//                UIUtils.displayError("Failed to add availability slots.");
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void manageAppointmentRequests() {
//        UIUtils.displayHeader("Pending Appointment Requests");
//        List<Appointment> requests =
//                appointmentController.getAppointmentRequests(doctor.getUserId());
//
//        if (requests.isEmpty()) {
//            UIUtils.displayError("No pending appointment requests.");
//            return;
//        }
//
//        for (Appointment request : requests) {
//            System.out.printf("""
//                ----------------------------------------
//                Request ID: %s
//                Patient ID: %s
//                Date & Time: %s
//                Type: %s
//                ----------------------------------------
//                """,
//                    request.getAppointmentId(),
//                    request.getPatientId(),
//                    UIUtils.formatDateTime(request.getDateTime()),
//                    request.getType()
//            );
//
//            boolean accept = UIUtils.promptForYesNo("Accept this appointment request?");
//            boolean success = appointmentController.handleAppointmentRequest(
//                    request.getAppointmentId(), accept);
//
//            if (success) {
//                UIUtils.displaySuccess("Request " + (accept ? "accepted" : "declined") + ".");
//            } else {
//                UIUtils.displayError("Failed to update request status.");
//            }
//            System.out.println();
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    // In DoctorMenu.java, add this new method:
//    private void createAppointmentOutcome(String appointmentId) {
//        UIUtils.displayHeader("Create Appointment Outcome Record");
//
//        Appointment appointment = appointmentController.getAppointment(appointmentId);
//        if (appointment == null) {
//            UIUtils.displayError("Appointment not found.");
//            return;
//        }
//
//        String consultationNotes = UIUtils.promptForString("Enter consultation notes");
//
//        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
//                appointmentId,
//                appointment.getDateTime(),
//                appointment.getType(),
//                consultationNotes,
//                doctor.getUserId(),
//                appointment.getPatientId()
//        );
//
//        while (true) {
//            String medication = UIUtils.promptForString(
//                    "Enter prescribed medication (or press Enter to finish)");
//            if (medication.isEmpty())
//                //add quantity for medication
//                break;
//
//            int quantity = UIUtils.promptForInt("Enter quantity for" + medication,1,100 );
//            record.addPrescription(medication, quantity);
//        }
//
//        if (AppointmentOutcomeManager.getInstance().createOutcomeRecord(record)) {
//            UIUtils.displaySuccess("Appointment outcome record created successfully!");
//        } else {
//            UIUtils.displayError("Failed to create appointment outcome record.");
//        }
//
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void viewUpcomingAppointments() {
//        UIUtils.displayHeader("Upcoming Appointments");
//        List<Appointment> appointments =
//                appointmentController.getDoctorUpcomingAppointments(doctor.getUserId());
//
//        if (appointments.isEmpty()) {
//            UIUtils.displayError("No upcoming appointments.");
//            return;
//        }
//
//        System.out.println("\nYour upcoming schedule:");
//        displayAppointments(appointments);
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void updateAppointmentStatus() {
//        UIUtils.displayHeader("Update Appointment Status");
//        List<Appointment> appointments =
//                appointmentController.getDoctorAppointments(doctor.getUserId());
//
//        // Filter for active appointments
//        List<Appointment> activeAppointments = appointments.stream()
//                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
//                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
//                .toList();
//
//        if (activeAppointments.isEmpty()) {
//            UIUtils.displayError("No active appointments to update.");
//            return;
//        }
//
//        System.out.println("\nActive Appointments:");
//        displayAppointments(activeAppointments);
//
//        String appointmentId = UIUtils.promptForString(
//                "Enter appointment ID to update (or press Enter to go back)");
//
//        if (!appointmentId.isEmpty()) {
//            System.out.println("\nSelect new status:");
//            System.out.println("1. Confirmed");
//            System.out.println("2. Completed");
//            System.out.println("3. Cancelled");
//
//            int statusChoice = UIUtils.promptForInt("Enter choice", 1, 3);
//
//            Appointment.AppointmentStatus newStatus = switch (statusChoice) {
//                case 1 -> Appointment.AppointmentStatus.CONFIRMED;
//                case 2 -> Appointment.AppointmentStatus.COMPLETED;
//                case 3 -> Appointment.AppointmentStatus.CANCELLED;
//                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
//            };
//
//            if (UIUtils.promptForYesNo("Confirm status update?")) {
//                boolean success = appointmentController.updateAppointmentStatus(
//                        appointmentId, newStatus);
//
//                if (success) {
//                    UIUtils.displaySuccess("Appointment status updated successfully!");
//                    if (newStatus == Appointment.AppointmentStatus.COMPLETED){
//                        createAppointmentOutcome(appointmentId);
//                    }
//                } else {
//                    UIUtils.displayError("Failed to update appointment status.");
//                }
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void displayAppointments(List<Appointment> appointments) {
//        for (Appointment appointment : appointments) {
//            System.out.printf("""
//                ----------------------------------------
//                Appointment ID: %s
//                Patient ID: %s
//                Date & Time: %s
//                Type: %s
//                Status: %s
//                ----------------------------------------
//                """,
//                    appointment.getAppointmentId(),
//                    appointment.getPatientId(),
//                    UIUtils.formatDateTime(appointment.getDateTime()),
//                    appointment.getType(),
//                    appointment.getStatus()
//            );
//        }
//    }
//}

package boundary.UserMenus;

import action.DoctorActions.*;
import boundary.Menu;
import util.UIUtils;
import entity.users.User;

import java.util.Map;

public class DoctorMenu implements Menu {
    private final User doctor;
    private final Map<Integer, DoctorAction> actions;

    public DoctorMenu(User doctor) {
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

    @Override
    public void display(User user) {
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
