package tests;

public class User {
    String username;
    String password;
    String errorMessage;

    // Constructor
    public User(String username, String password, String errorMessage) {
        this.username = username;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
