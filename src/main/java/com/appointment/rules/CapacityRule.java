package com.appointment.rules;

import com.appointment.model.Appointment;

public class CapacityRule implements BookingRuleStrategy {
    @Override
    public boolean isValid(Appointment appt) {
        return appt.getUser().getParticipants() <= 5;
    }
}
