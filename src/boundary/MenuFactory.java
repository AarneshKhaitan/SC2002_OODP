// boundary/MenuFactory.java
package boundary;

import boundary.UserMenus.AdminMenu;
import boundary.UserMenus.DoctorMenu;
import boundary.UserMenus.PatientMenu;
import boundary.UserMenus.PharmacistMenu;
import entity.users.User;

public class MenuFactory {
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