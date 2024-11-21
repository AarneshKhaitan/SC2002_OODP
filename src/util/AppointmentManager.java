package util;

import entity.Appointments.Appointment;
import entity.Appointments.AppointmentSlot;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
/**
 * Manages appointment scheduling, storage, and retrieval in the system.
 * Handles loading, saving, and validating appointment data, as well as managing available slots.
 */
public class AppointmentManager {
    private static final String APPOINTMENTS_FILE = "data/appointment_slots.csv"; // File storing appointment data
    private static final String SLOTS_FILE = "data/appointment.csv"; // File storing available slots
    private static AppointmentManager instance;

    private final List<Appointment> appointments; // List of all appointments
    private final List<AppointmentSlot> availableSlots; // List of available appointment slots
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    
    /**
     * Private constructor to prevent instantiation. Initializes lists and loads data from files.
     */
    private AppointmentManager() {
        appointments = new ArrayList<>();
        availableSlots = new ArrayList<>();
        loadData();
    }

        /**
     * Retrieves the singleton instance of the AppointmentManager.
     *
     * @return the instance of AppointmentManager
     */
    public static AppointmentManager getInstance() {
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    // Data Loading Methods
        /**
     * Loads appointment data from the file and populates the appointments list.
     */
    private void loadData() {
        loadAppointments();
        loadSlots();
    }

        /**
     * Loads appointments from the file and adds them to the appointments list.
     */
    private void loadAppointments() {
        File file = new File(APPOINTMENTS_FILE);
        if (!file.exists() || file.length() == 0) {
            createAppointmentsFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine();
            if (header == null){
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                if (data.length <6){
                    continue;
                }

                try{
                    String appointmentId = data[0].trim();
                    String patientId = data[1].trim();
                    String doctorId = data[2].trim();
                    LocalDateTime dateTime = LocalDateTime.parse(data[3].trim(), formatter);
                    String type = data[4].trim();
                    Appointment.AppointmentStatus status =
                            Appointment.AppointmentStatus.valueOf(data[5].trim());

                    Appointment appointment = new Appointment(appointmentId, patientId,
                            doctorId, dateTime, type);
                    appointment.setStatus(status);
                    appointments.add(appointment);
                } catch (Exception e){continue;}
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());

            createAppointmentsFile();
        }
    }

        /**
     * Loads available appointment slots from the file and adds them to the availableSlots list.
     */
    private void loadSlots() {
        File file = new File(SLOTS_FILE);
        if (!file.exists()) {
            createSlotsFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                String doctorId = data[0].trim();
                LocalDateTime startTime = LocalDateTime.parse(data[1].trim(), formatter);
                boolean isAvailable = Boolean.parseBoolean(data[2].trim());

                AppointmentSlot slot = new AppointmentSlot(doctorId, startTime);
                slot.setAvailable(isAvailable);
                availableSlots.add(slot);
            }
        } catch (IOException e) {
            System.err.println("Error loading slots: " + e.getMessage());
        }
    }


    // File Creation Methods

        /**
     * Creates the appointments file with the necessary header if it does not exist.
     */
    private void createAppointmentsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
            writer.println("AppointmentId,PatientId,DoctorId,DateTime,Type,Status");
        } catch (IOException e) {
            System.err.println("Error creating appointments file: " + e.getMessage());
        }
    }

    
    /**
     * Creates the slots file with the necessary header if it does not exist.
     */
    private void createSlotsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SLOTS_FILE))) {
            writer.println("DoctorId,StartTime,IsAvailable");
        } catch (IOException e) {
            System.err.println("Error creating slots file: " + e.getMessage());
        }
    }

    // Data Saving Methods

    
    /**
     * Saves the current appointments list to the appointments file.
     */
    private void saveAppointments() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
            writer.println("AppointmentId,PatientId,DoctorId,DateTime,Type,Status");

            for (Appointment appointment : appointments) {
                writer.printf("%s,%s,%s,%s,%s,%s%n",
                        appointment.getAppointmentId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId(),
                        appointment.getDateTime().format(formatter),
                        appointment.getType(),
                        appointment.getStatus()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

    
    /**
     * Saves the current available slots list to the slots file.
     */
    private void saveSlots() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SLOTS_FILE))) {
            writer.println("DoctorId,StartTime,IsAvailable");

            for (AppointmentSlot slot : availableSlots) {
                writer.printf("%s,%s,%b%n",
                        slot.getDoctorId(),
                        slot.getStartTime().format(formatter),
                        slot.isAvailable()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving slots: " + e.getMessage());
        }
    }

    // Public Methods for Appointment Management

    
    /**
     * Retrieves a list of available slots for a given doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of available appointment slots
     */
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.isAvailable() &&
                        slot.getStartTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(AppointmentSlot::getStartTime))
                .collect(Collectors.toList());
    }

    
    /**
     * Schedules an appointment if the slot is available.
     *
     * @param patientId the ID of the patient
     * @param doctorId  the ID of the doctor
     * @param dateTime  the date and time of the appointment
     * @param type      the type of appointment
     * @return {@code true} if the appointment is scheduled successfully, {@code false} otherwise
     */
    public boolean scheduleAppointment(String patientId, String doctorId,
                                       LocalDateTime dateTime, String type) {
        Optional<AppointmentSlot> slot = availableSlots.stream()
                .filter(s -> s.getDoctorId().equals(doctorId) &&
                        s.getStartTime().equals(dateTime) &&
                        s.isAvailable())
                .findFirst();

        if (slot.isEmpty()) return false;

        String appointmentId = generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patientId,
                doctorId, dateTime, type);
        appointments.add(appointment);
        slot.get().setAvailable(false);

        saveAppointments();
        saveSlots();
        return true;
    }

    
    /**
     * Cancels an appointment and makes the associated slot available again.
     *
     * @param appointmentId the ID of the appointment to cancel
     * @return {@code true} if the appointment was cancelled successfully, {@code false} otherwise
     */
    public boolean cancelAppointment(String appointmentId) {
        Optional<Appointment> appointment = appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst();

        if (appointment.isEmpty()) return false;

        appointment.get().setStatus(Appointment.AppointmentStatus.CANCELLED);

        // Make slot available again
        Optional<AppointmentSlot> slot = availableSlots.stream()
                .filter(s -> s.getDoctorId().equals(appointment.get().getDoctorId()) &&
                        s.getStartTime().equals(appointment.get().getDateTime()))
                .findFirst();

        slot.ifPresent(s -> s.setAvailable(true));

        saveAppointments();
        saveSlots();
        return true;
    }

        // Additional Methods for Appointment Management

    /**
     * Retrieves a list of appointments for a given doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of appointments for the doctor
     */
    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    
    /**
     * Retrieves a list of appointments for a given patient.
     *
     * @param patientId the ID of the patient
     * @return a list of appointments for the patient
     */
    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    
    /**
     * Retrieves a list of upcoming appointments for a doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of upcoming appointments for the doctor
     */
    public List<Appointment> getDoctorUpcomingAppointments(String doctorId) {
        LocalDateTime now = LocalDateTime.now();
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId) &&
                        a.getDateTime().isAfter(now) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of appointments for a specific doctor with a given status.
     *
     * @param doctorId The ID of the doctor.
     * @param status   The status of the appointments to filter.
     * @return A sorted list of appointments matching the doctor and status.
     */
    
    public List<Appointment> getAppointmentsByDoctorAndStatus(String doctorId,
                                                              Appointment.AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId) && a.getStatus() == status)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of an appointment.
     *
     * @param appointmentId The ID of the appointment to update.
     * @param status        The new status to set.
     * @return true if the appointment was updated successfully, false otherwise.
     */
    public boolean updateAppointmentStatus(String appointmentId,
                                           Appointment.AppointmentStatus status) {
        Optional<Appointment> appointment = appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst();

        if (appointment.isEmpty()) return false;

        appointment.get().setStatus(status);
        saveAppointments();
        return true;
    }

    /**
     * Adds new available slots for a specific doctor.
     *
     * @param doctorId The ID of the doctor.
     * @param slots    A list of LocalDateTime objects representing the new slots.
     * @return true after successfully adding the slots.
     */
    public boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots) {
        for (LocalDateTime slot : slots) {
            availableSlots.add(new AppointmentSlot(doctorId, slot));
        }
        saveSlots();
        return true;
    }

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId The ID of the appointment.
     * @return The Appointment object, or null if not found.
     */
    public Appointment getAppointment(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }

    // Utility Methods
    
    private String generateAppointmentId() {
        return "APT" + System.currentTimeMillis();
    }
    

    
    /**
     * Marks a specific slot as available for a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @param dateTime The date and time of the slot to mark as available.
     */
    public void markSlotAsAvailable(String doctorId, LocalDateTime dateTime) {
        availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.getStartTime().equals(dateTime))
                .findFirst()
                .ifPresent(slot -> slot.setAvailable(true));
        saveSlots();
    }

        /**
     * Marks a specific slot as unavailable for a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @param dateTime The date and time of the slot to mark as unavailable.
     */
    public void markSlotAsUnavailable(String doctorId, LocalDateTime dateTime) {
        availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.getStartTime().equals(dateTime))
                .findFirst()
                .ifPresent(slot -> slot.setAvailable(false));
        saveSlots();
    }

        /**
     * Updates an existing appointment with new details.
     *
     * @param appointment The updated Appointment object.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateAppointment(Appointment appointment) {
        int index = -1;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(
                    appointment.getAppointmentId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            appointments.set(index, appointment);
            saveAppointments();
            return true;
        }
        return false;
    }
    // Rescheduling Methods

    
    /**
     * Reschedules an appointment to a new date and time.
     *
     * @param appointmentId The ID of the appointment to reschedule.
     * @param newDateTime   The new date and time for the appointment.
     * @return true if the rescheduling was successful, false otherwise.
     */
    public boolean rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) {
        // Find the appointment
        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst();

        if (appointmentOpt.isEmpty()) {
            return false;
        }

        Appointment appointment = appointmentOpt.get();

        // Check if appointment can be rescheduled
        if (appointment.getStatus() == Appointment.AppointmentStatus.CANCELLED ||
                appointment.getStatus() == Appointment.AppointmentStatus.COMPLETED) {
            return false;
        }

        // Check if the new slot is available
        Optional<AppointmentSlot> newSlot = availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(appointment.getDoctorId()) &&
                        slot.getStartTime().equals(newDateTime) &&
                        slot.isAvailable())
                .findFirst();

        if (newSlot.isEmpty()) {
            return false;
        }

        // Store old datetime for slot management
        LocalDateTime oldDateTime = appointment.getDateTime();

        // Update the appointment
        appointment.setDateTime(newDateTime);

        // Handle slot availability
        markSlotAsAvailable(appointment.getDoctorId(), oldDateTime);
        markSlotAsUnavailable(appointment.getDoctorId(), newDateTime);

        // Reset status to REQUESTED if it was CONFIRMED
        if (appointment.getStatus() == Appointment.AppointmentStatus.CONFIRMED) {
            appointment.setStatus(Appointment.AppointmentStatus.REQUESTED);
        }

        // Save changes
        saveAppointments();
        saveSlots();

        return true;
    }

        /**
     * Retrieves a list of reschedulable appointments for a specific patient.
     *
     * @param patientId The ID of the patient.
     * @return A sorted list of reschedulable appointments.
     */
    public List<Appointment> getReschedulableAppointments(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED &&
                        a.getDateTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

       /**
     * Checks if a specific appointment is reschedulable.
     *
     * @param appointmentId The ID of the appointment to check.
     * @return true if the appointment can be rescheduled, false otherwise.
     */
    public boolean isAppointmentReschedulable(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .map(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED &&
                        a.getDateTime().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

       /**
     * Retrieves a list of available slots for rescheduling an appointment.
     *
     * @param appointmentId The ID of the appointment to reschedule.
     * @return A list of available AppointmentSlot objects.
     */
    public List<AppointmentSlot> getAvailableSlotsForRescheduling(String appointmentId) {
        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst();

        if (appointmentOpt.isEmpty()) {
            return new ArrayList<>();
        }

        String doctorId = appointmentOpt.get().getDoctorId();
        LocalDateTime currentAppointmentTime = appointmentOpt.get().getDateTime();

        return availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.isAvailable() &&
                        slot.getStartTime().isAfter(LocalDateTime.now()) &&
                        !slot.getStartTime().equals(currentAppointmentTime))
                .sorted(Comparator.comparing(AppointmentSlot::getStartTime))
                .collect(Collectors.toList());
    }

        /**
     * Validates a rescheduling request for an appointment.
     *
     * @param appointmentId The ID of the appointment.
     * @param newDateTime   The proposed new date and time.
     * @return true if the reschedule request is valid, false otherwise.
     */
    public boolean validateRescheduleRequest(String appointmentId, LocalDateTime newDateTime) {
        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst();

        if (appointmentOpt.isEmpty()) {
            return false;
        }

        Appointment appointment = appointmentOpt.get();

        // Check if appointment is reschedulable
        if (!isAppointmentReschedulable(appointmentId)) {
            return false;
        }

        // Check if new datetime is valid
        if (!newDateTime.isAfter(LocalDateTime.now())) {
            return false;
        }

        // Check if slot is available
        return availableSlots.stream()
                .anyMatch(slot -> slot.getDoctorId().equals(appointment.getDoctorId()) &&
                        slot.getStartTime().equals(newDateTime) &&
                        slot.isAvailable());
    }

    // Additional helper method for handling rescheduling conflicts
    
    /**
     * Handles conflicts for rescheduling by checking if the patient has other appointments
     * at the proposed time.
     *
     * @param patientId   The ID of the patient.
     * @param newDateTime The proposed new date and time.
     * @return true if no conflicts exist, false otherwise.
     */

    private boolean handleReschedulingConflicts(String patientId, LocalDateTime newDateTime) {
        // Check if patient has any other appointments at the same time
        return appointments.stream()
                .noneMatch(a -> a.getPatientId().equals(patientId) &&
                        a.getDateTime().equals(newDateTime) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED);
    }

        /**
     * Retrieves all appointments sorted by their date and time.
     *
     * @return A sorted list of all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

        /**
     * Retrieves a list of appointments filtered by their status.
     *
     * @param status The status to filter by.
     * @return A sorted list of appointments matching the status.
     */
    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == status)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

        /**
     * Retrieves a list of appointments scheduled for a specific date.
     *
     * @param date The date to filter by.
     * @return A sorted list of appointments on the given date.
     */
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getDateTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    
    /**
     * Retrieves appointment statistics such as total appointments, counts by status,
     * and counts by doctor.
     *
     * @return A map containing appointment statistics.
     */
    public Map<String, Integer> getAppointmentStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        // Total appointments
        stats.put("total", appointments.size());

        // Count by status
        for (Appointment.AppointmentStatus status : Appointment.AppointmentStatus.values()) {
            long count = appointments.stream()
                    .filter(a -> a.getStatus() == status)
                    .count();
            stats.put(status.toString().toLowerCase(), (int) count);
        }

        // Count by doctor
        Map<String, Long> doctorStats = appointments.stream()
                .collect(Collectors.groupingBy(
                        Appointment::getDoctorId,
                        Collectors.counting()
                ));

        // Add doctor statistics with prefix
        doctorStats.forEach((doctorId, count) ->
                stats.put("doctor_" + doctorId, count.intValue()));

        // Today's appointments
        LocalDate today = LocalDate.now();
        long todayCount = appointments.stream()
                .filter(a -> a.getDateTime().toLocalDate().equals(today))
                .count();
        stats.put("today", (int) todayCount);

        return stats;
    }

}
