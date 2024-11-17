package boundary;

import entity.UserRole;

public class MenuFactory {
    public Menu createMenu(UserRole role) {
        return switch (role) {
            case PATIENT -> new PatientMenu();
            case DOCTOR -> new DoctorMenu();
            case PHARMACIST -> new PharmacistMenu();
            case ADMINISTRATOR -> new AdminMenu();
        };
    }
}
