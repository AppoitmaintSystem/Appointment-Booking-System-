package com.appointment.test;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.appointment.model.User;
import com.appointment.notification.ObserverInterface;

public class NotificationMockTest {

    @Test
    void testNotificationMock() {
        ObserverInterface mockObserver = mock(ObserverInterface.class);

        User user = new User(1, "Sara", 2);

        mockObserver.notify(user, "Appointment reminder");

        verify(mockObserver, times(1)).notify(user, "Appointment reminder");
    }
}