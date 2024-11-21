/**
 * Represents an Administrator user in the system.
 * Inherits from the {@link User} class and adds additional properties specific to administrators.
 * An administrator has an associated age and is assigned the {@link UserRole#ADMINISTRATOR} role.
 * 
 * @author [Your Name]
 * @version 1.0
 */
package entity.users;

public class Administrator extends User {
    /**
     * The age of the administrator.
     */
    private int age;

    /**
     * Constructs an Administrator object with the specified ID, name, gender, and age.
     * The {@link UserRole#ADMINISTRATOR} role is automatically assigned.
     * 
     * @param adminId the unique identifier for the administrator
     * @param name    the name of the administrator
     * @param gender  the gender of the administrator
     * @param age     the age of the administrator
     */
    public Administrator(String adminId, String name, String gender, int age) {
        super(adminId, name, gender);
        this.age = age;
        this.role = UserRole.ADMINISTRATOR;
    }

    /**
     * Retrieves the age of the administrator.
     * 
     * @return the age of the administrator
     */
    public int getAge() { 
        return age; 
    }

    /**
     * Updates the age of the administrator.
     * 
     * @param age the new age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the Administrator, 
     * including the inherited details from the {@link User} class and the administrator's age.
     * 
     * @return a string representation of the Administrator
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}
