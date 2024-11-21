package action.PharmacistActions;

import entity.users.User;

/**
 * Abstract base class for pharmacist-specific actions.
 * Provides common properties or methods that are shared across various pharmacist operations.
 */
public interface PharmacistAction {
    void execute(User pharmacist);
}
