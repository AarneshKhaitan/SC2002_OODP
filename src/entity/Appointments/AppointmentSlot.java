package entity.Appointments;

import java.time.LocalDateTime;

/**
 * Represents an appointment slot for a doctor.
 * Contains information about the doctor, the start time of the appointment slot,
 * and the availability status of the slot.
 */
public class AppointmentSlot {

    private String doctorId;           // The ID of the doctor associated with the slot
    private LocalDateTime startTime;   // The start time of the appointment slot
    private boolean isAvailable;       // The availability status of the slot

    /**
     * Constructs a new AppointmentSlot instance.
     * The slot is initially marked as available.
     *
     * @param doctorId   the ID of the doctor associated with the slot
     * @param startTime  the start time of the appointment slot
     */
    public AppointmentSlot(String doctorId, LocalDateTime startTime) {
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.isAvailable = true; // Default availability is true
    }

    /**
     * Gets the ID of the doctor associated with the appointment slot.
     *
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Gets the start time of the appointment slot.
     *
     * @return the start time of the slot
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Checks if the appointment slot is available.
     *
     * @return {@code true} if the slot is available, {@code false} otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability of the appointment slot.
     * This is used to mark the slot as booked or available.
     *
     * @param available the new availability status for the slot
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Returns a string representation of the AppointmentSlot object.
     * The string includes the doctor's ID, start time, and availability status.
     *
     * @return a string representation of the appointment slot
     */
    @Override
    public String toString() {
        return "AppointmentSlot{" +
                "doctorId='" + doctorId + '\'' +
                ", startTime=" + startTime +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
