/**
 * A utility class providing common UI-related methods for user interaction.
 * This class includes methods for displaying messages, prompting user input,
 * and formatting dates and times.
 */
package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The {@code UIUtils} class provides static methods to handle various
 * user interface tasks, such as displaying messages, collecting input,
 * and validating data formats.
 */
public class UIUtils {

    /** A {@link Scanner} instance for reading user input. */
    private static final Scanner scanner = new Scanner(System.in);

    /** A {@link DateTimeFormatter} instance for formatting and parsing date-time strings. */
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Displays a formatted header with a given title.
     *
     * @param title the title to display in the header.
     */
    public static void displayHeader(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display.
     */
    public static void displayError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a success message.
     *
     * @param message the success message to display.
     */
    public static void displaySuccess(String message) {
        System.out.println("Success: " + message);
    }

    /**
     * Prompts the user for a string input.
     *
     * @param message the prompt message to display.
     * @return the user's input as a trimmed string.
     */
    public static String promptForString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user for a date-time input in the format "yyyy-MM-dd HH:mm".
     * Validates that the input is in the correct format and is a future date.
     *
     * @param message the prompt message to display.
     * @return the user's input as a {@link LocalDateTime}.
     */
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

    /**
     * Prompts the user for an integer input within a specified range.
     * Validates that the input is a number and falls within the range.
     *
     * @param message the prompt message to display.
     * @param min the minimum acceptable value.
     * @param max the maximum acceptable value.
     * @return the user's input as an integer.
     */
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

    /**
     * Prompts the user for a yes/no input.
     *
     * @param message the prompt message to display.
     * @return {@code true} if the user enters "Y", {@code false} if the user enters "N".
     */
    public static boolean promptForYesNo(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("Please enter Y or N.");
        }
    }

    /**
     * Prompts the user to press Enter to continue.
     * Waits for the user to press Enter before proceeding.
     */
    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Formats a {@link LocalDateTime} object into a string using the "yyyy-MM-dd HH:mm" format.
     *
     * @param dateTime the {@link LocalDateTime} to format.
     * @return the formatted date-time string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}
