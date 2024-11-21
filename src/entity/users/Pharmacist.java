/**
 * Represents a Pharmacist, which is a specific type of {@link User}.
 * A Pharmacist has additional attributes such as age and a predefined role as {@code UserRole.PHARMACIST}.
 */
package entity.users;

/**
 * The {@code Pharmacist} class extends the {@link User} class and represents
 * a pharmacist with specific attributes such as age and a role.
 */
public class Pharmacist extends User {
    /** The age of the pharmacist. */
    private int age;

    /**
     * Constructs a new {@code Pharmacist} with the specified details.
     *
     * @param pharmacistId the unique identifier for the pharmacist.
     * @param name the name of the pharmacist.
     * @param gender the gender of the pharmacist.
     * @param age the age of the pharmacist.
     */
    public Pharmacist(String pharmacistId, String name, String gender, int age) {
        super(pharmacistId, name, gender);
        this.age = age;
        this.role = UserRole.PHARMACIST;
    }

    /**
     * Retrieves the age of the pharmacist.
     *
     * @return the age of the pharmacist.
     */
    public int getAge() { return age; }

    /**
     * Updates the age of the pharmacist.
     *
     * @param age the new age of the pharmacist.
     */
    public void setAge(int age){this.age = age;}

    /**
     * Returns a string representation of the {@code Pharmacist}.
     * Includes details from the {@code User} class and the pharmacist's age.
     *
     * @return a formatted string containing the pharmacist's details.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}
