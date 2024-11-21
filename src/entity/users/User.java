package entity.users;

public abstract class User {
    protected String userId;
    protected String name;
    protected String gender;
    protected UserRole role;

    public User(String userId, String name, String gender) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
    }

    // Getters
    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public UserRole getRole() {
        return role;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setGender(String gender){
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Gender: %s | Role: %s", userId, name, gender, role);
    }
}