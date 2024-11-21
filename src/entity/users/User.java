/**
 * Represents an abstract base class for users in the system.
 * This class defines common properties and behaviors for all user types,
 * such as a unique identifier, name, gender, and role.
 */
package entity.users;

/**
 * The {@code User} class is an abstract class that serves as a base for specific user types.
 * It provides common attributes and methods for managing user details.
 */
public abstract class User {
    
    /** The unique identifier for the user. */
    protected String userId;

    /** The name of the user. */
    protected String name;

    /** The gender of the user. */
    protected String gender;

    /** The role of the user, defined by the {@link UserRole} enumeration. */
    protected UserRole role;

    /**
     * Constructs a new {@code User} with the specified details.
     *
     * @param userId the unique identifier for the user.
     * @param name the name of the user.
     * @param gender the gender of the user.
     */
    public User(String userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return the user's unique identifier.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the gender of the user.
     *
     * @return the user's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Retrieves the role of the user.
     *
     * @return the user's role, as defined by the {@link UserRole} enumeration.
     */
    public UserRole getRole() {
        return role;
    }

     /**
     * Updates the name of the user.
     *
     * @param name the new name of the user.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Updates the gender of the user.
     *
     * @param gender the new gender of the user.
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * Returns a string representation of the {@code User}.
     * Includes the user's ID, name, gender, and role.
     *
     * @return a formatted string containing the user's details.
     */
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Gender: %s | Role: %s", userId, name, gender, role);
    }
}
