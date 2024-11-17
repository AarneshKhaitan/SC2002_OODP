// main/HMSApplication.java
package main;

import boundary.LoginUI;

public class HMSApplication {
    public static void main(String[] args) {
        System.out.println("Starting Hospital Management System...");

        // Start with login screen
        LoginUI loginUI = new LoginUI();
        loginUI.display();
    }
}