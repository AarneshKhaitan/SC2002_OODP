package action.DoctorActions;

import entity.users.User;

/**
 * Abstract base class for doctor-specific actions.
 * Encapsulates shared functionality or contract enforcement for all doctor action implementations.
 */
public interface DoctorAction {
    void execute(User doctor);
}
