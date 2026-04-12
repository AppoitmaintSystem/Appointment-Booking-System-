package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.appointment.model.*;
import com.appointment.rules.*;

public class RulesTest {

    @Test
    void testDurationRuleValid() {
        DurationRule rule = new DurationRule();
        TimeSlot slot = new TimeSlot("10:00", 30);
        User user = new User(1, "Sima", 1);
        Appointment appt = new Appointment("2024-05-01", slot, user);

        assertTrue(rule.isValid(appt));
    }

    @Test
    void testDurationRuleInvalid() {
        DurationRule rule = new DurationRule();
        TimeSlot slot = new TimeSlot("10:00", 60);
        User user = new User(1, "Sima", 1);
        Appointment appt = new Appointment("2024-05-01", slot, user);

        assertFalse(rule.isValid(appt));
    }

    @Test
    void testCapacityRuleValid() {
        CapacityRule rule = new CapacityRule();
        TimeSlot slot = new TimeSlot("10:00", 30);
        User user = new User(1, "Sima", 3);

        Appointment appt = new Appointment("2024-05-01", slot, user);

        assertTrue(rule.isValid(appt));
    }

    @Test
    void testCapacityRuleInvalid() {
        CapacityRule rule = new CapacityRule();
        TimeSlot slot = new TimeSlot("10:00", 30);
        User user = new User(1, "Sima", 10);

        Appointment appt = new Appointment("2024-05-01", slot, user);

        assertFalse(rule.isValid(appt));
    }
}
