/**
 * Manages the inventory of medications and replenishment requests.
 * Provides methods for accessing, adding, and saving medication and replenishment data.
 * Utilizes {@link CSVDataLoader} to load data from persistent storage.
 */
package util;

import entity.Medications.Medication;
import entity.Medications.ReplenishmentRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * The {@code InventoryManager} class is responsible for managing the inventory
 * of medications and handling replenishment requests. It provides methods
 * to access the current inventory, add new replenishment requests, and save
 * data to persistent storage files.
 */
public class InventoryManager {

    /** File path for storing medication data. */
    private static final String MEDICINE_FILE = "data/medicine.csv";

    /** File path for storing replenishment request data. */
    private static final String REPLENISHMENT_FILE = "data/replenishment_requests.csv";

    /** A list of all medications in the inventory. */
    private final List<Medication> medications;

    /** A list of all replenishment requests. */
    private final List<ReplenishmentRequest> replenishmentRequests;

    /**
     * Constructs an {@code InventoryManager} and initializes the data by loading
     * medications and replenishment requests from CSV files.
     */
    public InventoryManager() {
        this.medications = CSVDataLoader.loadMedications();
        this.replenishmentRequests = CSVDataLoader.loadReplenishmentRequests();
    }

    /**
     * Retrieves the list of all medications in the inventory.
     *
     * @return a list of {@link Medication} objects representing the inventory.
     */
    public List<Medication> getMedications() {
        return medications;
    }

    /**
     * Retrieves the list of all replenishment requests.
     *
     * @return a list of {@link ReplenishmentRequest} objects.
     */
    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }

    /**
     * Adds a new replenishment request to the list and saves the updated data to the file.
     *
     * @param request the {@link ReplenishmentRequest} to add.
     */
    public void addReplenishmentRequest(ReplenishmentRequest request) {
        replenishmentRequests.add(request);
        saveReplenishmentRequests(replenishmentRequests);
    }

    /**
     * Saves the current list of medications to the persistent storage file.
     *
     * @param medications the list of {@link Medication} objects to save.
     */
    public static void saveMedications(List<Medication> medications) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEDICINE_FILE))) {
            writer.println("Name,Stock,LowStockAlert");
            for (Medication medication : medications) {
                writer.println(medication);
            }
        } catch (IOException e) {
            System.err.println("Error saving medications: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of replenishment requests to the persistent storage file.
     *
     * @param requests the list of {@link ReplenishmentRequest} objects to save.
     */
    public static void saveReplenishmentRequests(List<ReplenishmentRequest> requests) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(REPLENISHMENT_FILE))) {
            writer.println("ID,MedicationName,Quantity,PharmacistId,Status");
            for (ReplenishmentRequest request : requests) {
                writer.println(request);
            }
        } catch (IOException e) {
            System.err.println("Error saving replenishment requests: " + e.getMessage());
        }
    }
}
