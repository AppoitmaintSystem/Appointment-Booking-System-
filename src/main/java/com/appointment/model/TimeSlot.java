package com.appointment.model;

public class TimeSlot {
    private String startTime;
    private int duration;
    private boolean booked;

    public TimeSlot(String startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.booked = false;
    }

    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    public String getStartTime() { return startTime; }
    public int getDuration() { return duration; }
}
