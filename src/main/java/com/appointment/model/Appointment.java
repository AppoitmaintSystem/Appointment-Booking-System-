package com.appointment.model;

public class Appointment {
    private String date;
    private TimeSlot slot;
    private User user;

    public Appointment(String date, TimeSlot slot, User user) {
        this.date = date;
        this.slot = slot;
        this.user = user;
    }

    public Appointment(User user, TimeSlot slot) {
        this.user = user;
        this.slot = slot;
        this.date = "N/A"; 
    }

    public TimeSlot getSlot() { 
        return slot; 
    }

    public User getUser() { 
        return user; 
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}

