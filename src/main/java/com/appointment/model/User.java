package com.appointment.model;

/**
 * Represents a user in the appointment booking system.
 * Stores user information such as ID, name, and number of participants.
 * 
 * @author Sima
 * @version 1.0
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    private int id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * Number of participants associated with this user.
     */
    private int participants;

    /**
     * Creates a new User object.
     *
     * @param id the unique ID of the user
     * @param name the user's name
     * @param participants number of participants included with the user
     */
    public User(int id, String name, int participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    /**
     * Gets the number of participants.
     *
     * @return number of participants
     */
    public int getParticipants() { 
        return participants; 
    }

    /**
     * Gets the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's ID.
     *
     * @return the user ID
     */
    public Integer getId() {
        return id;
    }
}
