// boundary/MenuFactory.java
package boundary;

import boundary.UserMenus.AdminMenu;
import boundary.UserMenus.DoctorMenu;
import boundary.UserMenus.PatientMenu;
import boundary.UserMenus.PharmacistMenu;
import entity.users.User;
/**
 * Factory class for creating role-specific menus in the Hospital Management System.
 * Based on the user's role, it instantiates and returns the appropriate menu implementation.
 */
public class MenuFactory {
    
    /**
     * Creates a menu for the given user based on their role.
     *
     * @param user the user for whom the menu is being created
     * @return an instance of the appropriate menu implementation
     * @throws IllegalArgumentException if the user's role is invalid or unsupported
     */
    
    public Menu createMenu(User user) {
        return switch (user.getRole()) {
            case PATIENT -> new PatientMenu(user);
            case DOCTOR -> new DoctorMenu(user);
            case ADMINISTRATOR -> new AdminMenu(user);
            case PHARMACIST -> new PharmacistMenu(user);
            default -> throw new IllegalArgumentException("Invalid user role: " + user.getRole());
        };
    }
}
