// entity/AppointmentSlot.java
package entity.Appointments;

import java.time.LocalDateTime;

public class AppointmentSlot {
    private String doctorId;
    private LocalDateTime startTime;
    private boolean isAvailable;

    public AppointmentSlot(String doctorId, LocalDateTime startTime) {
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.isAvailable = true;
    }

    // Getters
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getStartTime() { return startTime; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "AppointmentSlot{" +
                "doctorId='" + doctorId + '\'' +
                ", startTime=" + startTime +
                ", isAvailable=" + isAvailable +
                '}';
    }
}