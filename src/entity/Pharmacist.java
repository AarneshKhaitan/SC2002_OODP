package entity;

public class Pharmacist extends User {
    private int age;

    public Pharmacist(String pharmacistId, String name, String gender, int age) {
        super(pharmacistId, name, gender);
        this.age = age;
        this.role = UserRole.PHARMACIST;
    }

    public int getAge() { return age; }
}