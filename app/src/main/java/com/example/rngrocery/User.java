package com.example.rngrocery;
// Define a User class to represent user information
public class User {

    // Private fields to store user information
    private String username;
    private String emailId;
    private String password;

    // Constructor to initialize user with provided information
    public User(String username, String emailId, String password) {
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

    // Getter method to retrieve the username
    public String getUsername() {
        return username;
    }

    // Setter method to set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method to retrieve the email ID
    public String getEmailId() {
        return emailId;
    }

    // Setter method to set the email ID
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    // Getter method to retrieve the password
    public String getPassword() {
        return password;
    }

    // Setter method to set the password
    public void setPassword(String password) {
        this.password = password;
    }
}
