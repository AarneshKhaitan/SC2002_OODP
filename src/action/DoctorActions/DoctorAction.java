package action.DoctorActions;

import entity.users.User;

/**
 * Abstract base class for all doctor-related actions.
 * Provides a common interface or shared functionality for specific doctor actions.
 */
public interface DoctorAction {
    void execute(User doctor);
}
