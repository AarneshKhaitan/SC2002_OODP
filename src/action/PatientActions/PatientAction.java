package action.PatientActions;

import entity.users.User;

/**
 * Abstract base class for patient-specific actions.
 * Defines common properties or methods shared among various patient actions.
 */
public interface PatientAction {
    void execute(User patient);
}
