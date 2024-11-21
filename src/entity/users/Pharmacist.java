package entity.users;

public class Pharmacist extends User {
    private int age;

    public Pharmacist(String pharmacistId, String name, String gender, int age) {
        super(pharmacistId, name, gender);
        this.age = age;
        this.role = UserRole.PHARMACIST;
    }

    public int getAge() { return age; }
    public void setAge(int age){this.age = age;}

    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}