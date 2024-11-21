// boundary/Menu.java
package boundary;

import entity.users.User;
/**
 * Represents a general menu interface in the Hospital Management System.
 * Classes implementing this interface must define how a menu is displayed for a specific user.
 */
public interface Menu {
    
    /**
     * Displays the menu for the given user.
     *
     * @param user the user for whom the menu is being displayed
     */
    void display(User user);
}
