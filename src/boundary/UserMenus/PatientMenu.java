//package boundary.UserMenus;
//
//import boundary.Menu;
//import util.UIUtils;
//import controller.MedicalRecords.MedicalRecordController;
//import controller.interfaces.PatientAppointmentController;
//import controller.AppointmentControllers.PatientAppointmentControllerImpl;
//import entity.Appointments.Appointment;
//import entity.Appointments.AppointmentOutcomeRecord;
//import entity.Appointments.AppointmentSlot;
//import entity.Medications.Prescription;
//import entity.Records.Diagnosis;
//import entity.Records.MedicalRecord;
//import entity.Records.Treatment;
//import entity.users.User;
//import util.AppointmentOutcomeManager;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Scanner;
//
//public class PatientMenu implements Menu {
//    private final Scanner scanner = new Scanner(System.in);
//    private final PatientAppointmentController appointmentController;
//    private final MedicalRecordController medicalRecordController;
//    private final User patient;
//
//    public PatientMenu(User patient) {
//        this.patient = patient;
//        this.appointmentController = PatientAppointmentControllerImpl.getInstance();
//        this.medicalRecordController = MedicalRecordController.getInstance();
//    }
//
//    @Override
//    public void display(User user) {
//        while (true) {
//            UIUtils.displayHeader("Patient Menu");
//            System.out.println("1. View Medical Record");
//            System.out.println("2. Update Personal Information");
//            System.out.println("3. View Available Appointment Slots");
//            System.out.println("4. Schedule an Appointment");
//            System.out.println("5. View My Appointments");
//            System.out.println("6. Cancel Appointment");
//            System.out.println("7. Reschedule Appointment");
//            System.out.println("8. View Past Appointment Records");
//            System.out.println("9. Logout");
//
//            int choice = UIUtils.promptForInt("Choose an option", 1, 9);
//
//            switch (choice) {
//                case 1 -> viewMedicalRecord();
//                case 2 -> updatePersonalInfo();
//                case 3 -> viewAvailableSlots();
//                case 4 -> scheduleAppointment();
//                case 5 -> viewMyAppointments();
//                case 6 -> cancelAppointment();
//                case 7 -> rescheduleAppointment();
//                case 8 -> viewPastAppointments();
//                case 9 -> {
//                    UIUtils.displaySuccess("Logging out...");
//                    return;
//                }
//                default -> System.out.println("Invalid option. Please try again.");
//            }
//        }
//    }
//
//    // Medical Record Methods
//    private void viewMedicalRecord() {
//        UIUtils.displayHeader("Medical Record");
//
//        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(
//                patient.getUserId());
//
//        if (record == null) {
//            UIUtils.displayError("Medical record not found.");
//            return;
//        }
//
//        // Display personal information
//        System.out.println("\nPersonal Information:");
//        System.out.printf("Patient ID: %s%n", record.getPatientId());
//        System.out.printf("Name: %s%n", record.getName());
//        System.out.printf("Date of Birth: %s%n", record.getDateOfBirth());
//        System.out.printf("Gender: %s%n", record.getGender());
//        System.out.printf("Blood Type: %s%n", record.getBloodType());
//        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
//        System.out.printf("Email: %s%n", record.getEmailAddress());
//
//        // Display diagnoses
//        System.out.println("\nPast Diagnoses:");
//        List<Diagnosis> diagnoses = record.getDiagnoses();
//        if (diagnoses.isEmpty()) {
//            System.out.println("No past diagnoses recorded.");
//        } else {
//            diagnoses.forEach(diagnosis -> System.out.printf("""
//                            Date: %s
//                            Doctor: %s
//                            Condition: %s
//                            Notes: %s
//                            ----------------------------------------
//                            """,
//                    diagnosis.getDate(),
//                    diagnosis.getDoctorId(),
//                    diagnosis.getCondition(),
//                    diagnosis.getNotes()
//            ));
//        }
//
//        // Display treatments
//        System.out.println("\nTreatments:");
//        List<Treatment> treatments = record.getTreatments();
//        if (treatments.isEmpty()) {
//            System.out.println("No treatments recorded.");
//        } else {
//            treatments.forEach(treatment -> System.out.printf("""
//                            Date: %s
//                            Doctor: %s
//                            Type: %s
//                            Medications: %s
//                            Instructions: %s
//                            ----------------------------------------
//                            """,
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
//    private void updatePersonalInfo() {
//        UIUtils.displayHeader("Update Personal Information");
//        MedicalRecord record = medicalRecordController.getPatientMedicalRecord(
//                patient.getUserId());
//
//        if (record == null) {
//            UIUtils.displayError("Medical record not found.");
//            return;
//        }
//
//        System.out.println("\nCurrent Contact Information:");
//        System.out.printf("Phone Number: %s%n", record.getPhoneNumber());
//        System.out.printf("Email: %s%n", record.getEmailAddress());
//
//        String phoneNumber = UIUtils.promptForString(
//                "\nEnter new phone number (or press Enter to keep current)");
//        String email = UIUtils.promptForString(
//                "Enter new email (or press Enter to keep current)");
//
//        // Only update if new values are provided
//        if (!phoneNumber.isEmpty() || !email.isEmpty()) {
//            String newPhone = phoneNumber.isEmpty() ? record.getPhoneNumber() : phoneNumber;
//            String newEmail = email.isEmpty() ? record.getEmailAddress() : email;
//
//            if (medicalRecordController.updateContactInfo(
//                    patient.getUserId(), newPhone, newEmail)) {
//                UIUtils.displaySuccess("Contact information updated successfully!");
//            } else {
//                UIUtils.displayError("Failed to update contact information.");
//            }
//        }
//
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void viewAvailableSlots() {
//        UIUtils.displayHeader("Available Appointment Slots");
//        String doctorId = UIUtils.promptForString("Enter Doctor ID");
//
//        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlots(doctorId);
//        if (availableSlots.isEmpty()) {
//            UIUtils.displayError("No available slots found for this doctor.");
//            return;
//        }
//
//        System.out.println("\nAvailable Slots:");
//        for (int i = 0; i < availableSlots.size(); i++) {
//            System.out.printf("%d. %s%n", i + 1,
//                    UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//
//    private void scheduleAppointment() {
//        UIUtils.displayHeader("Schedule New Appointment");
//        String doctorId = UIUtils.promptForString("Enter Doctor ID");
//
//        List<AppointmentSlot> availableSlots = appointmentController.getAvailableSlots(doctorId);
//        if (availableSlots.isEmpty()) {
//            UIUtils.displayError("No available slots found for this doctor.");
//            return;
//        }
//
//        // Display available slots
//        System.out.println("\nAvailable Slots:");
//        for (int i = 0; i < availableSlots.size(); i++) {
//            System.out.printf("%d. %s%n", i + 1,
//                    UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
//        }
//
//        // Get slot selection
//        int slotChoice = UIUtils.promptForInt("Select slot number", 1, availableSlots.size());
//        AppointmentSlot selectedSlot = availableSlots.get(slotChoice - 1);
//
//        // Display appointment types
//        System.out.println("\nAppointment Types:");
//        System.out.println("1. Consultation");
//        System.out.println("2. X-ray");
//        System.out.println("3. Blood Test");
//
//        int typeChoice = UIUtils.promptForInt("Select appointment type", 1, 3);
//        String type = switch (typeChoice) {
//            case 1 -> "Consultation";
//            case 2 -> "X-ray";
//            case 3 -> "Blood Test";
//            default -> throw new IllegalStateException("Unexpected value: " + typeChoice);
//        };
//
//        // Schedule the appointment
//        boolean success = appointmentController.scheduleAppointment(
//                patient.getUserId(),
//                doctorId,
//                selectedSlot.getStartTime(),
//                type
//        );
//
//        if (success) {
//            UIUtils.displaySuccess("Appointment scheduled successfully!");
//        } else {
//            UIUtils.displayError("Failed to schedule appointment. Please try again.");
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void viewMyAppointments() {
//        UIUtils.displayHeader("My Appointments");
//        List<Appointment> appointments =
//                appointmentController.getPatientAppointments(patient.getUserId());
//
//        if (appointments.isEmpty()) {
//            UIUtils.displayError("You have no appointments scheduled.");
//            return;
//        }
//
//        displayAppointments(appointments);
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void cancelAppointment() {
//        UIUtils.displayHeader("Cancel Appointment");
//        List<Appointment> appointments =
//                appointmentController.getPatientAppointments(patient.getUserId());
//
//        if (appointments.isEmpty()) {
//            UIUtils.displayError("You have no appointments to cancel.");
//            return;
//        }
//
//        // Display only cancellable appointments
//        List<Appointment> cancellableAppointments = appointments.stream()
//                .filter(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
//                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED)
//                .toList();
//
//        if (cancellableAppointments.isEmpty()) {
//            UIUtils.displayError("No appointments available for cancellation.");
//            return;
//        }
//
//        System.out.println("\nCancellable Appointments:");
//        displayAppointments(cancellableAppointments);
//
//        String appointmentId = UIUtils.promptForString("Enter appointment ID to cancel (or press Enter to go back)");
//
//        if (!appointmentId.isEmpty()) {
//            if (UIUtils.promptForYesNo("Are you sure you want to cancel this appointment?")) {
//                boolean success = appointmentController.cancelAppointment(appointmentId);
//                if (success) {
//                    UIUtils.displaySuccess("Appointment cancelled successfully!");
//                } else {
//                    UIUtils.displayError("Failed to cancel appointment. Please check the ID and try again.");
//                }
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void rescheduleAppointment() {
//        UIUtils.displayHeader("Reschedule Appointment");
//        List<Appointment> reschedulableAppointments =
//                appointmentController.getReschedulableAppointments(patient.getUserId());
//
//        if (reschedulableAppointments.isEmpty()) {
//            UIUtils.displayError("No appointments available for rescheduling.");
//            return;
//        }
//
//        System.out.println("\nReschedulable Appointments:");
//        displayAppointments(reschedulableAppointments);
//
//        String appointmentId = UIUtils.promptForString("Enter appointment ID to reschedule (or press Enter to go back)");
//        if (appointmentId.isEmpty()) return;
//
//        List<AppointmentSlot> availableSlots =
//                appointmentController.getAvailableSlotsForRescheduling(appointmentId);
//
//        if (availableSlots.isEmpty()) {
//            UIUtils.displayError("No available slots for rescheduling.");
//            return;
//        }
//
//        System.out.println("\nAvailable Slots:");
//        for (int i = 0; i < availableSlots.size(); i++) {
//            System.out.printf("%d. %s%n", i + 1,
//                    UIUtils.formatDateTime(availableSlots.get(i).getStartTime()));
//        }
//
//        int slotChoice = UIUtils.promptForInt("Select new slot number", 1, availableSlots.size());
//        LocalDateTime newDateTime = availableSlots.get(slotChoice - 1).getStartTime();
//
//        if (UIUtils.promptForYesNo("Confirm rescheduling to " +
//                UIUtils.formatDateTime(newDateTime) + "?")) {
//            boolean success = appointmentController.rescheduleAppointment(
//                    appointmentId, newDateTime);
//
//            if (success) {
//                UIUtils.displaySuccess("Appointment rescheduled successfully!");
//            } else {
//                UIUtils.displayError("Failed to reschedule appointment. Please try again.");
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void viewPastAppointments() {
//        UIUtils.displayHeader("Past Appointments and Outcomes");
//        List<Appointment> appointments =
//                appointmentController.getPatientAppointments(patient.getUserId());
//
//        List<Appointment> pastAppointments = appointments.stream()
//                .filter(a -> a.getStatus() == Appointment.AppointmentStatus.COMPLETED)
//                .toList();
//
//        if (pastAppointments.isEmpty()) {
//            UIUtils.displayError("No past appointments found.");
//            return;
//        }
//
//        displayAppointments(pastAppointments);
//        System.out.println("\nOutcome Records:");
//        for (Appointment appointment : pastAppointments) {
//            AppointmentOutcomeRecord outcome =
//                    AppointmentOutcomeManager.getInstance().getOutcomeRecord(appointment.getAppointmentId());
//
//            if (outcome != null) {
//                System.out.printf("""
//                ----------------------------------------
//                Appointment ID: %s
//                Consultation Notes: %s
//
//                Prescribed Medications:
//                """,
//                        outcome.getAppointmentId(),
//                        outcome.getConsultationNotes()
//                );
//
//                for (Prescription prescription : outcome.getPrescriptions()) {
//                    System.out.printf("- %s (Status: %s)%n",
//                            prescription.getMedicationName(),
//                            prescription.getStatus().getDisplayName()
//                    );
//                }
//                System.out.println("----------------------------------------");
//            }
//        }
//        UIUtils.pressEnterToContinue();
//    }
//
//    private void displayAppointments(List<Appointment> appointments) {
//        for (Appointment appointment : appointments) {
//            System.out.printf("""
//                            ----------------------------------------
//                            Appointment ID: %s
//                            Doctor ID: %s
//                            Date & Time: %s
//                            Type: %s
//                            Status: %s
//                            """,
//                    appointment.getAppointmentId(),
//                    appointment.getDoctorId(),
//                    UIUtils.formatDateTime(appointment.getDateTime()),
//                    appointment.getType(),
//                    appointment.getStatus()
//            );
//        }
//        System.out.println("----------------------------------------");
//    }
//}
//
package boundary.UserMenus;

import action.PatientActions.*;
import boundary.Menu;
import controller.MedicalRecords.MedicalRecordController;
import controller.AppointmentControllers.PatientAppointmentControllerImpl;
import entity.users.User;
import util.UIUtils;

import java.util.HashMap;
import java.util.Map;

public class PatientMenu implements Menu {
    private final Map<Integer, PatientAction> actions = new HashMap<>();

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

    @Override
    public void display(User user) {
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

