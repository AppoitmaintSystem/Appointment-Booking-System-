package com.appointment.service;

import java.util.ArrayList;
import java.util.List;

import com.appointment.admin.Admin;
import com.appointment.model.*;
import com.appointment.rules.BookingRuleStrategy;

public class BookingService {

    private List<BookingRuleStrategy> rules = new ArrayList<>();
    private Appointment[] appointments = new Appointment[50];
    private TimeSlot[] availableSlots = new TimeSlot[50];

    private int count = 0;
    private int slotCount = 0;

    public void addRule(BookingRuleStrategy rule) {
        rules.add(rule);
    }

    public boolean addAvailableSlot(int time) {
        for (int i = 0; i < slotCount; i++) {
            if (availableSlots[i] != null &&
                availableSlots[i].getStartTime().equals(String.valueOf(time))) {
                System.out.println("Slot already exists.");
                return false;
            }
        }

        availableSlots[slotCount++] = new TimeSlot(String.valueOf(time), 60);
        System.out.println("Available slot added at time: " + time);
        return true;
    }

    public Appointment bookFromAvailableSlot(int time, User user) {
        for (int i = 0; i < slotCount; i++) {
            TimeSlot slot = availableSlots[i];

            if (slot != null &&
                slot.getStartTime().equals(String.valueOf(time)) &&
                !slot.isBooked()) {

                Appointment appt = new Appointment("N/A", slot, user);

                for (BookingRuleStrategy rule : rules) {
                    if (!rule.isValid(appt)) {
                        return null;
                    }
                }

                slot.setBooked(true);
                appointments[count++] = appt;
                return appt;
            }
        }

        return null;
    }

    public Appointment modify(int oldTime, int newTime) {
        Appointment oldAppt = findAppointmentByTime(oldTime);

        if (oldAppt == null) {
            System.out.println("No appointment found at this time.");
            return null;
        }

        User user = oldAppt.getUser();

        cancel(oldTime);

        Appointment newAppt = bookFromAvailableSlot(newTime, user);

        if (newAppt != null) {
            System.out.println("Appointment modified successfully!");
            return newAppt;
        } else {
            System.out.println("Modification failed.");
            return null;
        }
    }

    public void cancel(int cancelTime) {
        boolean found = false;

        for (int i = 0; i < count; i++) {
            Appointment a = appointments[i];

            if (a != null &&
                a.getSlot().getStartTime().equals(String.valueOf(cancelTime))) {

                a.getSlot().setBooked(false);
                appointments[i] = null;
                found = true;
                System.out.println("Appointment canceled!");
                break;
            }
        }

        if (!found) {
            System.out.println("No appointment found at this time.");
        }
    }

    public void printSchedule() {
        boolean hasAppointments = false;

        for (int i = 0; i < count; i++) {
            if (appointments[i] != null) {
                hasAppointments = true;
                break;
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments scheduled.");
            return;
        }

        System.out.println("=== Schedule ===");
        for (int i = 0; i < count; i++) {
            Appointment a = appointments[i];
            if (a != null) {
                System.out.println("User: " + a.getUser().getName()
                        + " | Time: " + a.getSlot().getStartTime()
                        + " | Date: " + a.getDate());
            }
        }
    }

    public void viewAvailableSlots() {
        System.out.println("=== Available Slots ===");

        boolean hasAvailable = false;

        for (int i = 0; i < slotCount; i++) {
            TimeSlot slot = availableSlots[i];

            if (slot != null && !slot.isBooked()) {
                System.out.println("Time: " + slot.getStartTime());
                hasAvailable = true;
            }
        }

        if (!hasAvailable) {
            System.out.println("No available slots.");
        }
    }

    public Appointment findAppointmentByTime(int time) {
        for (int i = 0; i < count; i++) {
            Appointment a = appointments[i];

            if (a != null &&
                a.getSlot().getStartTime().equals(String.valueOf(time))) {
                return a;
            }
        }
        return null;
    }

    public void adminCancel(Admin admin, Appointment appt) {
        if (!admin.isLoggedIn()) {
            throw new RuntimeException("Admin must be logged in");
        }

        appt.getSlot().setBooked(false);

        for (int i = 0; i < count; i++) {
            if (appointments[i] == appt) {
                appointments[i] = null;
                break;
            }
        }
    }
}