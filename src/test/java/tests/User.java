package tests;

public class User {
    String username;
    String password;
    String errorMessage;
    String firstName;
    String lastName;
    String zipCode;

    // Constructor
    public User(String username, String password, String errorMessage, String firstName, String lastName, String zipCode) {
        this.username = username;
        this.password = password;
        this.errorMessage = errorMessage;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }

    // Getters and Setters (يمكن إضافة هذه الحقول حسب الحاجة)
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getZipCode() {
        return zipCode;
    }
}
