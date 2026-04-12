package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.appointment.model.*;

public class AppointmentTypeTest {

    @Test
    void testUrgentType() {
        AppointmentType type = new Urgent();
        assertEquals("Urgent", type.getTypeName());
    }
}
