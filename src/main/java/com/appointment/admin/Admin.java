package com.appointment.admin;

import java.util.logging.Logger;

public class Admin {

    private static final Logger logger =
            Logger.getLogger(Admin.class.getName());

    private boolean loggedIn = false;

    private String username = "admin";
    private String credential = "1234";

    public boolean login(String user, String pass) {

        if (user.equals(username) && pass.equals(credential)) {

            loggedIn = true;

            logger.info("Login successful!");

            return true;

        } else {

            logger.warning("Invalid credentials.");

            return false;
        }
    }

    public void logout() {

        loggedIn = false;

        logger.info("Logged out.");
    }

    public boolean isLoggedIn() {
    	

        return loggedIn;
    }
}