package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.appointment.admin.Admin;

public class AdminTest {

    @Test
    void testInitialState() {
        Admin admin = new Admin();
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void testLoginSuccess() {
        Admin admin = new Admin();
        assertTrue(admin.login("admin", "1234"));
        assertTrue(admin.isLoggedIn());
    }

    @Test
    void testLoginFailWrongUsername() {
        Admin admin = new Admin();
        assertFalse(admin.login("wrongUser", "1234"));
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void testLoginFailWrongPassword() {
        Admin admin = new Admin();
        assertFalse(admin.login("admin", "wrongPass"));
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void testLoginFailBothWrong() {
        Admin admin = new Admin();
        assertFalse(admin.login("wrong", "wrong"));
        assertFalse(admin.isLoggedIn());
    }

    @Test
    void testLogout() {
        Admin admin = new Admin();
        admin.login("admin", "1234");
        admin.logout();
        assertFalse(admin.isLoggedIn());
    }
}