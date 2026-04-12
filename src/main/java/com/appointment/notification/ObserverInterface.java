package com.appointment.notification;

import com.appointment.model.User;

public interface ObserverInterface {
    void notify(User user, String message);
}
