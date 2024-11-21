package action.PatientActions;

import entity.users.User;

/**
 * Represents the contract for patient-specific actions in the system.
 * 
 * <p>
 * This interface defines a method that must be implemented by all classes
 * representing actions that can be performed by a patient. It ensures that
 * each action has an `execute` method, which takes the patient performing
 * the action as a parameter.
 * </p>
 * 
 * <p>
 * Implementing classes may define specific actions such as scheduling an appointment,
 * viewing medical records, or canceling an appointment.
 * </p>
 */
public interface PatientAction {

    /**
     * Executes the patient-specific action.
     * 
     * @param patient The {@link User} object representing the patient performing the action.
     */
    void execute(User patient);
}
