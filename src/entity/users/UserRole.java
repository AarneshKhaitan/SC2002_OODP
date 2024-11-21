/**
 * Represents the roles that a user can have in the system.
 * This enumeration defines the distinct roles available, such as Patient, Doctor, Pharmacist, and Administrator.
 */
package entity.users;

/**
 * The {@code UserRole} enum defines the possible roles a {@link User} can have.
 * These roles are used to categorize users and determine their permissions or responsibilities within the system.
 */
public enum UserRole {

    /** Represents a user with the role of a Patient. */
    PATIENT,

    /** Represents a user with the role of a Doctor. */
    DOCTOR,

    /** Represents a user with the role of a Pharmacist. */
    PHARMACIST,

    /** Represents a user with the role of an Administrator. */
    ADMINISTRATOR
}
