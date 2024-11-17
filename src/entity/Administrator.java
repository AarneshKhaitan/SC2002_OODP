package entity;

public class Administrator extends User {
    private int age;

    public Administrator(String adminId, String name, String gender, int age) {
        super(adminId, name, gender);
        this.age = age;
        this.role = UserRole.ADMINISTRATOR;
    }

    public int getAge() { return age; }
}
