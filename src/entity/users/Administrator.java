package entity.users;

public class Administrator extends User {
    private int age;

    public Administrator(String adminId, String name, String gender, int age) {
        super(adminId, name, gender);
        this.age = age;
        this.role = UserRole.ADMINISTRATOR;
    }

    public int getAge() { return age; }
    public void setAge(int age){this.age = age;}

    @Override
    public String toString() {
        return super.toString() + String.format(" | Age: %d", age);
    }
}