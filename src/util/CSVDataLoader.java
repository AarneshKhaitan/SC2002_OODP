package util;

import entity.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CSVDataLoader {
    private static final String STAFF_FILE = "data/staff.csv";
    private static final String PATIENT_FILE = "data/patient.csv";
    private static final String MEDICINE_FILE = "data/medicine.csv";
    private static final String REPLENISHMENT_FILE = "data/replenishment_requests.csv";


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
}