package action.PharmacistActions;

import entity.users.User;

/**
 * Represents the contract for pharmacist-specific actions in the system.
 * 
 * <p>
 * This interface defines a method that must be implemented by all classes
 * representing actions that can be performed by a pharmacist. It ensures that
 * each action has an `execute` method, which takes the pharmacist performing
 * the action as a parameter.
 * </p>
 * 
 * <p>
 * Implementing classes may define specific actions such as managing inventory,
 * updating prescription statuses, or viewing pending prescription requests.
 * </p>
 */
public interface PharmacistAction {

     /**
     * Executes the pharmacist-specific action.
     * 
     * @param pharmacist The {@link User} object representing the pharmacist performing the action.
     */
    void execute(User pharmacist);
}
