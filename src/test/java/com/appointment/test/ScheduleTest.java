package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.appointment.model.Schedule;
import com.appointment.model.TimeSlot;

public class ScheduleTest {

    @Test
    void testAddAndGetAvailableSlots() {
        Schedule schedule = new Schedule();

        TimeSlot s1 = new TimeSlot("10:00", 30);
        TimeSlot s2 = new TimeSlot("11:00", 30);
        TimeSlot s3 = new TimeSlot("12:00", 30);

        s2.setBooked(true);

        schedule.addSlot(s1);
        schedule.addSlot(s2);
        schedule.addSlot(s3);

        assertEquals(2, schedule.getAvailableSlots().size());
    }
}
