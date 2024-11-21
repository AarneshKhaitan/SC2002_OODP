package action.DoctorActions;

import entity.users.User;

/**
 * Represents the contract for doctor-specific actions in the system.
 * 
 * <p>
 * This interface defines a method that must be implemented by all classes
 * representing actions that can be performed by a doctor. It ensures that
 * each action has an `execute` method, which takes the doctor performing
 * the action as a parameter.
 * </p>
 * 
 * <p>
 * Implementing classes may define specific actions, such as adding diagnoses,
 * updating treatments, or managing appointments.
 * </p>
 */
public interface DoctorAction {
     /**
     * Executes the doctor-specific action.
     * 
     * @param doctor The {@link User} object representing the doctor performing the action.
     */
    void execute(User doctor);
}
