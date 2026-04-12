package com.appointment.rules;

import com.appointment.model.Appointment;

public interface BookingRuleStrategy {
    boolean isValid(Appointment appointment);
}
