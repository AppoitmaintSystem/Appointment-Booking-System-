package com.appointment.admin;

public class Admin {

    private boolean loggedIn = false;

    private String username = "admin";
    private String password = "1234";

    public boolean login(String user, String pass) {
        if (user.equals(username) && pass.equals(password)) {
            loggedIn = true;
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Logged out.");
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}