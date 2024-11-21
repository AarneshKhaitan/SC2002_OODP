package entity.users;

/**
 * Represents a Doctor user in the system.
 * Inherits from the {@link User} class and includes additional properties specific to doctors.
 * A doctor has an associated age and is assigned the {@link UserRole#DOCTOR} role.
 * 
 * @author [Your Name]
 * @version 1.0
 */

public class Doctor extends User {
    /**
     * The age of the doctor.
     */
    private int age;

    /**
     * Constructs a Doctor object with the specified ID, name, gender, and age.
     * The {@link UserRole#DOCTOR} role is automatically assigned.
     * 
     * @param doctorId the unique identifier for the doctor
     * @param name     the name of the doctor
     * @param gender   the gender of the doctor
     * @param age      the age of the doctor
     */
    public Doctor(String doctorId, String name, String gender, int age) {
        super(doctorId, name, gender);
        this.age = age;
        this.role = UserRole.DOCTOR;
    }

    /**
     * Retrieves the age of the doctor.
     * 
     * @return the age of the doctor
     */
    public int getAge() { 
        return age; 
    }

    /**
     * Updates the age of the doctor.
     * 
     * @param age the new age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the Doctor, 
     * including the inherited details from the {@link User} class and the doctor's age.
     * 
     * @return a string representation of the Doctor
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}
