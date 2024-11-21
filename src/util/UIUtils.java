// util/UIUtils.java
package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UIUtils {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void displayHeader(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void displayError(String message) {
        System.out.println("Error: " + message);
    }

    public static void displaySuccess(String message) {
        System.out.println("Success: " + message);
    }

    public static String promptForString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine().trim();
    }

    public static LocalDateTime promptForDateTime(String message) {
        while (true) {
            System.out.print(message + " (yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                if (dateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Please enter a future date and time.");
                    continue;
                }
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    public static int promptForInt(String message, int min, int max) {
        while (true) {
            System.out.print(message + ": ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static boolean promptForYesNo(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("Please enter Y or N.");
        }
    }

    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}