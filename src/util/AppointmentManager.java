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

public class AppointmentManager {
    private static final String APPOINTMENTS_FILE = "data/appointment_slots.csv";
    private static final String SLOTS_FILE = "data/appointment.csv";
    private static AppointmentManager instance;

    private final List<Appointment> appointments;
    private final List<AppointmentSlot> availableSlots;
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AppointmentManager() {
        appointments = new ArrayList<>();
        availableSlots = new ArrayList<>();
        loadData();
    }

    public static AppointmentManager getInstance() {
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    // Data Loading Methods
    private void loadData() {
        loadAppointments();
        loadSlots();
    }
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
    private void createAppointmentsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
            writer.println("AppointmentId,PatientId,DoctorId,DateTime,Type,Status");
        } catch (IOException e) {
            System.err.println("Error creating appointments file: " + e.getMessage());
        }
    }

    private void createSlotsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SLOTS_FILE))) {
            writer.println("DoctorId,StartTime,IsAvailable");
        } catch (IOException e) {
            System.err.println("Error creating slots file: " + e.getMessage());
        }
    }

    // Data Saving Methods
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
    public List<AppointmentSlot> getAvailableSlots(String doctorId) {
        return availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.isAvailable() &&
                        slot.getStartTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(AppointmentSlot::getStartTime))
                .collect(Collectors.toList());
    }

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

    public List<Appointment> getAppointmentsForDoctor(String doctorId) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsForPatient(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    public List<Appointment> getDoctorUpcomingAppointments(String doctorId) {
        LocalDateTime now = LocalDateTime.now();
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId) &&
                        a.getDateTime().isAfter(now) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctorAndStatus(String doctorId,
                                                              Appointment.AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId) && a.getStatus() == status)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

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

    public boolean addDoctorSlots(String doctorId, List<LocalDateTime> slots) {
        for (LocalDateTime slot : slots) {
            availableSlots.add(new AppointmentSlot(doctorId, slot));
        }
        saveSlots();
        return true;
    }

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

    public void markSlotAsAvailable(String doctorId, LocalDateTime dateTime) {
        availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.getStartTime().equals(dateTime))
                .findFirst()
                .ifPresent(slot -> slot.setAvailable(true));
        saveSlots();
    }

    public void markSlotAsUnavailable(String doctorId, LocalDateTime dateTime) {
        availableSlots.stream()
                .filter(slot -> slot.getDoctorId().equals(doctorId) &&
                        slot.getStartTime().equals(dateTime))
                .findFirst()
                .ifPresent(slot -> slot.setAvailable(false));
        saveSlots();
    }

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

    public List<Appointment> getReschedulableAppointments(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED &&
                        a.getDateTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    public boolean isAppointmentReschedulable(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .map(a -> a.getStatus() != Appointment.AppointmentStatus.CANCELLED &&
                        a.getStatus() != Appointment.AppointmentStatus.COMPLETED &&
                        a.getDateTime().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

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
    private boolean handleReschedulingConflicts(String patientId, LocalDateTime newDateTime) {
        // Check if patient has any other appointments at the same time
        return appointments.stream()
                .noneMatch(a -> a.getPatientId().equals(patientId) &&
                        a.getDateTime().equals(newDateTime) &&
                        a.getStatus() != Appointment.AppointmentStatus.CANCELLED);
    }
    public List<Appointment> getAllAppointments() {
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByStatus(Appointment.AppointmentStatus status) {
        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == status)
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getDateTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .collect(Collectors.toList());
    }
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