package com.appointment.model;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<TimeSlot> slots = new ArrayList<>();

    public void addSlot(TimeSlot slot) {
        slots.add(slot);
    }

    public List<TimeSlot> getAvailableSlots() {
        List<TimeSlot> available = new ArrayList<>();
        for (TimeSlot s : slots) {
            if (!s.isBooked()) available.add(s);
        }
        return available;
    }
}
