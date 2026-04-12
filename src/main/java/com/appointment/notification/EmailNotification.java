package com.appointment.notification;

import com.appointment.model.User;

public class EmailNotification implements ObserverInterface {
    @Override
    public void notify(User user, String message) {
        System.out.println("Email sent to " + user.getName() + ": " + message);
    }
}
