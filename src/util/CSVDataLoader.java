/**
 * A utility class for loading and saving data to and from CSV files.
 * Handles operations related to staff, patients, medications, and replenishment requests.
 */
package util;

import entity.Medications.Medication;
import entity.Medications.ReplenishmentRequest;
import entity.users.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The {@code CSVDataLoader} class provides methods to load and save application data
 * from and to CSV files. It supports loading user data (staff and patients), medication data,
 * and replenishment requests, as well as saving updated staff data.
 */
public class CSVDataLoader {

    /** File path for storing staff data. */
    private static final String STAFF_FILE = "data/staff.csv";

    /** File path for storing patient data. */
    private static final String PATIENT_FILE = "data/patient.csv";

    /** File path for storing medication data. */
    private static final String MEDICINE_FILE = "data/medicine.csv";

    /** File path for storing replenishment request data. */
    private static final String REPLENISHMENT_FILE = "data/replenishment_requests.csv";

    /**
     * Loads all users (staff and patients) into a single map.
     *
     * @return a map of user IDs to {@link User} objects.
     */
    public static Map<String, User> loadAllUsers() {
        Map<String, User> users = new HashMap<>();
        try {
            users.putAll(loadStaffData());
            users.putAll(loadPatientData());
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Loads staff data from the CSV file.
     *
     * @return a map of staff IDs to {@link User} objects.
     */
    public static Map<String, User> loadStaffData() {
        Map<String, User> staff = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(STAFF_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String staffId = data[0].trim();
                String name = data[1].trim();
                String role = data[2].trim();
                String gender = data[3].trim();
                int age = Integer.parseInt(data[4].trim());

                User user = switch (role.toUpperCase()) {
                    case "DOCTOR" -> new Doctor(staffId, name, gender, age);
                    case "PHARMACIST" -> new Pharmacist(staffId, name, gender, age);
                    case "ADMINISTRATOR" -> new Administrator(staffId, name, gender, age);
                    default -> throw new IllegalArgumentException("Invalid role: " + role);
                };
                staff.put(staffId, user);
            }
        } catch (IOException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
        }
        return staff;
    }

    /**
     * Loads patient data from the CSV file.
     *
     * @return a map of patient IDs to {@link Patient} objects.
     */
    public static Map<String, User> loadPatientData() {
        Map<String, User> patients = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENT_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                String patientId = data[0].trim();
                String name = data[1].trim();
                LocalDate dob = LocalDate.parse(data[2].trim());
                String gender = data[3].trim();
                String bloodType = data[4].trim();
                String email = data[5].trim();

                Patient patient = new Patient(patientId, name, dob, gender, bloodType, email);
                patients.put(patientId, patient);
            }
        } catch (IOException e) {
            System.err.println("Error loading patient data: " + e.getMessage());
        }
        return patients;
    }

    /**
     * Loads medication data from the CSV file.
     *
     * @return a list of {@link Medication} objects.
     */
    public static List<Medication> loadMedications() {
        List<Medication> medications = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICINE_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                medications.add(new Medication(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }
        } catch (IOException e) {
            System.err.println("Error loading medications: " + e.getMessage());
        }
        return medications;
    }

    /**
     * Loads replenishment request data from the CSV file.
     *
     * @return a list of {@link ReplenishmentRequest} objects.
     */
    public static List<ReplenishmentRequest> loadReplenishmentRequests() {
        List<ReplenishmentRequest> requests = new ArrayList<>();
        File file = new File(REPLENISHMENT_FILE);
        if (!file.exists()) return requests;

        try (BufferedReader reader = new BufferedReader(new FileReader(REPLENISHMENT_FILE))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                ReplenishmentRequest request = new ReplenishmentRequest(data[1], Integer.parseInt(data[2]), data[3]);
                if (data[4].equals("Approved")) request.approve();
                if (data[4].equals("Rejected")) request.reject();
                requests.add(request);
            }
        } catch (IOException e) {
            System.err.println("Error loading replenishment requests: " + e.getMessage());
        }
        return requests;
    }

    /**
     * Saves staff data to the CSV file.
     *
     * @param staff a map of staff IDs to {@link User} objects to save.
     */
    public static void saveStaffData(Map<String, User> staff) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STAFF_FILE))) {
            writer.println("Staff ID,Name,Role,Gender,Age");
            for (User user : staff.values()) {
                String age = "";
                if (user instanceof Doctor doctor) {
                    age = String.valueOf(doctor.getAge());
                } else if (user instanceof Pharmacist pharmacist) {
                    age = String.valueOf(pharmacist.getAge());
                } else if (user instanceof Administrator admin) {
                    age = String.valueOf(admin.getAge());
                }
                writer.printf("%s,%s,%s,%s,%s%n",
                        user.getUserId(),
                        user.getName(),
                        user.getRole(),
                        user.getGender(),
                        age);
            }
        } catch (IOException e) {
            System.err.println("Error saving staff data: " + e.getMessage());
        }
    }
}
