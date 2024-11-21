package util;

import entity.Medications.Medication;
import entity.Medications.ReplenishmentRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class InventoryManager {
    private static final String MEDICINE_FILE = "data/medicine.csv";
    private static final String REPLENISHMENT_FILE = "data/replenishment_requests.csv";
    private final List<Medication> medications;
    private final List<ReplenishmentRequest> replenishmentRequests;

    public InventoryManager() {
        this.medications = CSVDataLoader.loadMedications();
        this.replenishmentRequests = CSVDataLoader.loadReplenishmentRequests();
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }

    public void addReplenishmentRequest(ReplenishmentRequest request) {
        replenishmentRequests.add(request);
        saveReplenishmentRequests(replenishmentRequests);
    }

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
