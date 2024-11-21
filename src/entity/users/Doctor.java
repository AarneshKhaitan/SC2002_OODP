package entity.users;

public class Doctor extends User {
    private int age;

    public Doctor(String doctorId, String name, String gender, int age) {
        super(doctorId, name, gender);
        this.age = age;
        this.role = UserRole.DOCTOR;
    }

    public int getAge() { return age; }
    public void setAge(int age){this.age = age;}

    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}