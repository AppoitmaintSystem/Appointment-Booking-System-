package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.appointment.model.TimeSlot;

public class TimeSlotTest {

    @Test
    void testTimeSlot() {
        TimeSlot slot = new TimeSlot("10:00", 30);

        assertEquals("10:00", slot.getStartTime());
        assertEquals(30, slot.getDuration());
        assertFalse(slot.isBooked());

        slot.setBooked(true);
        assertTrue(slot.isBooked());
    }
}
