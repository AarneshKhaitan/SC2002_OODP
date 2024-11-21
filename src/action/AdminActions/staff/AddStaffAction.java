package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import entity.users.Doctor;
import entity.users.Pharmacist;
import entity.users.User;
import util.StaffManager;

import java.util.Scanner;

/**
 * Handles the addition of new staff members to the system.
 * <p>
 * This action allows administrators to add new staff members by entering their
 * details such as ID, name, role (Doctor or Pharmacist), gender, and age. It validates
 * the role and utilizes the staff manager to save the new staff member.
 * </p>
 */
public class AddStaffAction implements AdminAction {
    private final StaffManager staffManager;

    /**
     * Constructs an {@code AddStaffAction} instance.
     *
     * @param staffManager the staff manager used to manage staff data
     */
    public AddStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    /**
     * Executes the action to add a new staff member.
     * <p>
     * Prompts the administrator to input the staff details, validates the role, and creates
     * a new staff member object. The new staff member is then added to the system using the
     * staff manager. If an invalid role is entered, an appropriate error message is displayed.
     * </p>
     * <ul>
     *     <li>Roles supported: Doctor, Pharmacist.</li>
     *     <li>Validates input and handles errors for invalid roles.</li>
     * </ul>
     */
    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID: ");
        String staffId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role (DOCTOR/PHARMACIST): ");
        String role = scanner.nextLine().toUpperCase();
        System.out.print("Enter Gender (Male/Female): ");
        String gender = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        User newStaff = switch (role) {
            case "DOCTOR" -> new Doctor(staffId, name, gender, age);
            case "PHARMACIST" -> new Pharmacist(staffId, name, gender, age);
            default -> throw new IllegalArgumentException("Invalid role.");
        };

        try {
            staffManager.addStaff(newStaff);
            System.out.println("New staff member added successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
