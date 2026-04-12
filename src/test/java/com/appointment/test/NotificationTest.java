package com.appointment.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.appointment.model.User;
import com.appointment.notification.EmailNotification;
import com.appointment.notification.ObserverInterface;
import com.appointment.notification.RealEmailNotification;

public class NotificationTest {

    @Test
    void testObserverInterfaceImplementation() {
        ObserverInterface observer = new EmailNotification();
        assertNotNull(observer);
    }

    @Test
    void testEmailNotificationNotify() {
        User user = new User(1, "Sara", 2);
        EmailNotification emailNotification = new EmailNotification();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        emailNotification.notify(user, "Appointment reminder");

        System.setOut(originalOut);

        String printed = output.toString();
        assertTrue(printed.contains("Email sent to Sara: Appointment reminder"));
    }

    @Test
    void testRealEmailNotificationConstructor() {
        RealEmailNotification realEmail =
                new RealEmailNotification("test@gmail.com", "123456");

        assertNotNull(realEmail);
    }
}