package entity;

public class Doctor extends User {
    private int age;

    public Doctor(String doctorId, String name, String gender, int age) {
        super(doctorId, name, gender);
        this.age = age;
        this.role = UserRole.DOCTOR;
    }

    public int getAge() { return age; }
}