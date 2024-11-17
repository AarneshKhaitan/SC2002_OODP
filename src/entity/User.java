package entity;

public abstract class User {
    protected String userId;
    protected String name;
    protected String gender;
    protected String email;
    protected UserRole role;

    public User(String userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public UserRole getRole() { return role; }
}
