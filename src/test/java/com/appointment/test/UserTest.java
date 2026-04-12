package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.appointment.model.User;

public class UserTest {

    @Test
    void testUserFields() {
        User u = new User(1, "Sima", 3);

        assertEquals(1, u.getId());
        assertEquals("Sima", u.getName());
        assertEquals(3, u.getParticipants());
    }
}
