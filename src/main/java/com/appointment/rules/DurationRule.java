package com.appointment.rules;

import com.appointment.model.Appointment;

public class DurationRule implements BookingRuleStrategy {
    @Override
    public boolean isValid(Appointment appt) {
        return appt.getSlot().getDuration() <= 30;
    }
}
