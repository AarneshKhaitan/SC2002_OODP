package action.AdminActions.staff;

import action.AdminActions.AdminAction;
import entity.users.Doctor;
import entity.users.Pharmacist;
import entity.users.User;
import util.StaffManager;

import java.util.Scanner;

public class AddStaffAction implements AdminAction {
    private final StaffManager staffManager;

    public AddStaffAction(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

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