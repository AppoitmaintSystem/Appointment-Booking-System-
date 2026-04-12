package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.appointment.model.Appointment;
import com.appointment.model.TimeSlot;
import com.appointment.model.User;

public class AppointmentTest {

    @Test
    void testAppointmentConstructorAndGetters() {
        TimeSlot slot = new TimeSlot("10:00", 30);
        User user = new User(1, "Sima", 2);

        Appointment appt = new Appointment("2024-05-01", slot, user);

        assertEquals("2024-05-01", appt.getDate());
        assertEquals(slot, appt.getSlot());
        assertEquals(user, appt.getUser());
    }

    @Test
    void testSetDate() {
        TimeSlot slot = new TimeSlot("10:00", 30);
        User user = new User(1, "Sima", 2);

        Appointment appt = new Appointment("2024-05-01", slot, user);

        appt.setDate("2024-05-02");

        assertEquals("2024-05-02", appt.getDate());
    }

    @Test
    void testSlotAndUserNotNull() {
        TimeSlot slot = new TimeSlot("11:00", 45);
        User user = new User(2, "Lina", 1);

        Appointment appt = new Appointment("2024-06-10", slot, user);

        assertNotNull(appt.getSlot());
        assertNotNull(appt.getUser());
    }

    @Test
    void testAppointmentConstructorUserSlotOnly() {
        User user = new User(10, "Rama", 1);
        TimeSlot slot = new TimeSlot("15:00", 20);

        Appointment appt = new Appointment(user, slot);

        assertEquals(user, appt.getUser());
        assertEquals(slot, appt.getSlot());
        assertEquals("N/A", appt.getDate());
    }
}

