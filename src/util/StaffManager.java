package util;

import entity.users.Administrator;
import entity.users.Doctor;
import entity.users.Pharmacist;
import entity.users.User;

import java.util.*;
import java.util.stream.Collectors;

public class StaffManager {
    private final Map<String, User> staff;

    public StaffManager() {
        this.staff = CSVDataLoader.loadStaffData();
    }

    public Map<String, User> getStaff() {
        return staff;
    }

    public void addStaff(User newStaff) {
        if (staff.containsKey(newStaff.getUserId())) {
            throw new IllegalArgumentException("Staff with this ID already exists.");
        }
        staff.put(newStaff.getUserId(), newStaff);
        saveStaffData();
    }

    public void updateStaff(String staffId, String newName, String newGender, Integer newAge) {
        User staffMember = staff.get(staffId);
        if (staffMember == null) {
            throw new IllegalArgumentException("Staff member not found.");
        }

        // Use setters to update name, gender, and age
        if (newName != null && !newName.isBlank()) {
            staffMember.setName(newName); // Use setter
        }

        if (newGender != null && !newGender.isBlank()) {
            staffMember.setGender(newGender); // Use setter
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

    public void removeStaff(String staffId) {
        if (staff.remove(staffId) == null) {
            throw new IllegalArgumentException("Staff member not found.");
        }
        saveStaffData();
    }

    public List<User> filterStaffByRole(String role) {
        return staff.values().stream()
                .filter(user -> user.getRole().toString().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    public List<User> filterStaffByGender(String gender) {
        return staff.values().stream()
                .filter(user -> user.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

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
                    return false; // Non-age-based roles won't match
                })
                .collect(Collectors.toList());
    }

    public void saveStaffData() {
        CSVDataLoader.saveStaffData(staff);
    }
}
