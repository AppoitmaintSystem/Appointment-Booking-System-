package com.appointment.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.appointment.admin.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.User;
import com.appointment.service.BookingService;

public class BookingServiceTest {

    @Test
    void testAddAvailableSlotSuccess() {
        BookingService service = new BookingService();
        boolean result = service.addAvailableSlot(10);
        assertTrue(result);
    }

    @Test
    void testAddAvailableSlotDuplicate() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        boolean result = service.addAvailableSlot(10);
        assertFalse(result);
    }

    @Test
    void testBookFromAvailableSlotSuccess() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 2);

        Appointment appt = service.bookFromAvailableSlot(10, user);

        assertNotNull(appt);
        assertEquals("Sima", appt.getUser().getName());
        assertEquals("10", appt.getSlot().getStartTime());
    }

    @Test
    void testBookFromAvailableSlotFailNoSlot() {
        BookingService service = new BookingService();
        User user = new User(1, "Sima", 2);

        Appointment appt = service.bookFromAvailableSlot(99, user);

        assertNull(appt);
    }

    @Test
    void testBookFromAvailableSlotFailRule() {
        BookingService service = new BookingService();
        service.addRule(appt -> false);
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 2);

        Appointment appt = service.bookFromAvailableSlot(10, user);

        assertNull(appt);
    }

    @Test
    void testBookFromAvailableSlotAlreadyBooked() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);

        User user1 = new User(1, "Sima", 2);
        User user2 = new User(2, "Lina", 1);

        Appointment first = service.bookFromAvailableSlot(10, user1);
        Appointment second = service.bookFromAvailableSlot(10, user2);

        assertNotNull(first);
        assertNull(second);
    }

    @Test
    void testCancelByTimeFound() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 1);

        Appointment appt = service.bookFromAvailableSlot(10, user);
        assertNotNull(appt);

        service.cancel(10);

        assertNull(service.findAppointmentByTime(10));
    }

    @Test
    void testCancelByTimeNotFound() {
        BookingService service = new BookingService();
        service.cancel(99);

        assertNull(service.findAppointmentByTime(99));
    }

    @Test
    void testPrintScheduleEmpty() {
        BookingService service = new BookingService();

        service.printSchedule();

        assertNull(service.findAppointmentByTime(10));
    }

    @Test
    void testPrintScheduleWithAppointment() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 1);

        service.bookFromAvailableSlot(10, user);
        service.printSchedule();

        assertNotNull(service.findAppointmentByTime(10));
    }

    @Test
    void testViewAvailableSlotsEmpty() {
        BookingService service = new BookingService();

        service.viewAvailableSlots();

        assertNull(service.findAppointmentByTime(8));
    }

    @Test
    void testViewAvailableSlotsWithData() {
        BookingService service = new BookingService();

        assertTrue(service.addAvailableSlot(8));
        assertTrue(service.addAvailableSlot(9));

        service.viewAvailableSlots();

        assertNull(service.findAppointmentByTime(8));
        assertNull(service.findAppointmentByTime(9));
    }

    @Test
    void testFindAppointmentByTimeFound() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 1);

        service.bookFromAvailableSlot(10, user);

        Appointment found = service.findAppointmentByTime(10);

        assertNotNull(found);
        assertEquals("Sima", found.getUser().getName());
    }

    @Test
    void testFindAppointmentByTimeNotFound() {
        BookingService service = new BookingService();

        Appointment found = service.findAppointmentByTime(99);

        assertNull(found);
    }

    @Test
    void testAdminCancelSuccess() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 1);

        Appointment appt = service.bookFromAvailableSlot(10, user);
        Admin admin = new Admin();

        admin.login("admin", "1234");
        service.adminCancel(admin, appt);

        assertNull(service.findAppointmentByTime(10));
    }

    @Test
    void testAdminCancelFail() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);
        User user = new User(1, "Sima", 1);

        Appointment appt = service.bookFromAvailableSlot(10, user);
        Admin admin = new Admin();

        assertThrows(RuntimeException.class, () -> service.adminCancel(admin, appt));
    }

    @Test
    void testBookFromAvailableSlotFailBecauseAlreadyCanceledThenRebook() {
        BookingService service = new BookingService();
        service.addAvailableSlot(10);

        User user1 = new User(1, "Sima", 1);
        User user2 = new User(2, "Lina", 1);

        Appointment first = service.bookFromAvailableSlot(10, user1);
        assertNotNull(first);

        service.cancel(10);

        Appointment second = service.bookFromAvailableSlot(10, user2);

        assertNotNull(second);
        assertEquals("Lina", second.getUser().getName());
    }

    @Test
    void testModifySuccess() {
        BookingService service = new BookingService();

        service.addAvailableSlot(10);
        service.addAvailableSlot(11);

        User user = new User(1, "Sima", 1);
        Appointment first = service.bookFromAvailableSlot(10, user);

        assertNotNull(first);

        Appointment updated = service.modify(10, 11);

        assertNotNull(updated);
        assertEquals("11", updated.getSlot().getStartTime());
        assertNull(service.findAppointmentByTime(10));
    }

    @Test
    void testModifyFailNoOldAppointment() {
        BookingService service = new BookingService();
        service.addAvailableSlot(11);

        Appointment updated = service.modify(10, 11);

        assertNull(updated);
    }
}