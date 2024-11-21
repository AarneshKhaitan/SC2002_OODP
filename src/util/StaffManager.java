/**
 * Manages the operations related to staff members, including adding, updating, removing,
 * and filtering staff data. This class interacts with {@link CSVDataLoader} for
 * persistent storage and retrieval of staff information.
 */
package util;

import entity.users.Administrator;
import entity.users.Doctor;
import entity.users.Pharmacist;
import entity.users.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code StaffManager} class provides functionalities for managing staff members
 * such as doctors, pharmacists, and administrators. It allows adding new staff,
 * updating their details, filtering based on various criteria, and saving data persistently.
 */
public class StaffManager {

    /** A map containing staff members, where the key is the staff ID and the value is a {@link User}. */
    private final Map<String, User> staff;

    /**
     * Constructs a {@code StaffManager} and initializes the staff data by loading it from a CSV file.
     */
    public StaffManager() {
        this.staff = CSVDataLoader.loadStaffData();
    }

    /**
     * Retrieves all staff members.
     *
     * @return a map of staff members, where the key is the staff ID and the value is the {@link User}.
     */
    public Map<String, User> getStaff() {
        return staff;
    }

    /**
     * Adds a new staff member to the system.
     *
     * @param newStaff the new {@link User} object to add.
     * @throws IllegalArgumentException if a staff member with the same ID already exists.
     */
    public void addStaff(User newStaff) {
        if (staff.containsKey(newStaff.getUserId())) {
            throw new IllegalArgumentException("Staff with this ID already exists.");
        }
        staff.put(newStaff.getUserId(), newStaff);
        saveStaffData();
    }

    /**
     * Updates the details of an existing staff member.
     *
     * @param staffId the unique identifier of the staff member to update.
     * @param newName the new name for the staff member, or {@code null} to keep the existing name.
     * @param newGender the new gender for the staff member, or {@code null} to keep the existing gender.
     * @param newAge the new age for the staff member, or {@code null} to keep the existing age.
     * @throws IllegalArgumentException if the staff member is not found or if the role does not support age updates.
     */
    public void updateStaff(String staffId, String newName, String newGender, Integer newAge) {
        User staffMember = staff.get(staffId);
        if (staffMember == null) {
            throw new IllegalArgumentException("Staff member not found.");
        }

        if (newName != null && !newName.isBlank()) {
            staffMember.setName(newName);
        }

        if (newGender != null && !newGender.isBlank()) {
            staffMember.setGender(newGender);
        }

        if (newAge != null) {
            if (staffMember instanceof Doctor doctor) {
                doctor.setAge(newAge);
            } else if (staffMember instanceof Pharmacist pharmacist) {
                pharmacist.setAge(newAge);
            } else if (staffMember instanceof Administrator admin) {
                admin.setAge(newAge);
            } else {
                throw new IllegalArgumentException("Unsupported role for age update.");
            }
        }

        saveStaffData();
    }

    /**
     * Removes a staff member from the system.
     *
     * @param staffId the unique identifier of the staff member to remove.
     * @throws IllegalArgumentException if the staff member is not found.
     */
    public void removeStaff(String staffId) {
        if (staff.remove(staffId) == null) {
            throw new IllegalArgumentException("Staff member not found.");
        }
        saveStaffData();
    }

    /**
     * Filters staff members by role.
     *
     * @param role the role to filter staff by (e.g., "Doctor", "Pharmacist").
     * @return a list of {@link User} objects matching the specified role.
     */
    public List<User> filterStaffByRole(String role) {
        return staff.values().stream()
                .filter(user -> user.getRole().toString().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    /**
     * Filters staff members by gender.
     *
     * @param gender the gender to filter staff by (e.g., "Male", "Female").
     * @return a list of {@link User} objects matching the specified gender.
     */
    public List<User> filterStaffByGender(String gender) {
        return staff.values().stream()
                .filter(user -> user.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    /**
     * Filters staff members by an age range.
     *
     * @param minAge the minimum age (inclusive).
     * @param maxAge the maximum age (inclusive).
     * @return a list of {@link User} objects whose age falls within the specified range.
     */
    public List<User> filterStaffByAgeRange(int minAge, int maxAge) {
        return staff.values().stream()
                .filter(user -> {
                    if (user instanceof Doctor doctor) {
                        return doctor.getAge() >= minAge && doctor.getAge() <= maxAge;
                    } else if (user instanceof Pharmacist pharmacist) {
                        return pharmacist.getAge() >= minAge && pharmacist.getAge() <= maxAge;
                    } else if (user instanceof Administrator admin) {
                        return admin.getAge() >= minAge && admin.getAge() <= maxAge;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Saves the staff data to persistent storage.
     */
    public void saveStaffData() {
        CSVDataLoader.saveStaffData(staff);
    }
}
