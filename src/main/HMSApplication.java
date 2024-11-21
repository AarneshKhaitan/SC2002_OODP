// main/HMSApplication.java
package main;

import boundary.LoginUI;
/**
 * The main entry point for the Hospital Management System (HMS) application.
 * Initializes and starts the application by displaying the login screen.
 */
public class HMSApplication {
        /**
     * The main method to launch the HMS application.
     * 
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        System.out.println("Starting Hospital Management System...");

        // Start with login screen
        LoginUI loginUI = new LoginUI();
        loginUI.display();
    }
}
