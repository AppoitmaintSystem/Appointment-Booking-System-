package com.appointment.app;

import java.util.Scanner;

import com.appointment.admin.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.User;
import com.appointment.notification.RealEmailNotification;
import com.appointment.service.BookingService;

public class Menu {

    private static final String ADMIN_EMAIL = "sara7maher@gmail.com";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookingService service = new BookingService();

        RealEmailNotification emailService = new RealEmailNotification(
                ADMIN_EMAIL,
                "sqzmwcqvhasndbis"
        );

        Admin admin = new Admin();

        while (true) {
            System.out.println("\nAre you Admin or User?");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                break;
            }
            int role = scanner.nextInt();

            switch (role) {

                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.next();

                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    if (!admin.login(username, password)) {
                        break;
                    }

                    while (true) {
                        System.out.println("\n=== Admin Menu ===");
                        System.out.println("1. Add Available Slot");
                        System.out.println("2. View Available Slots");
                        System.out.println("3. Logout");
                        System.out.println("4. Back");
                        System.out.print("Choose an option: ");

                        if (!scanner.hasNextInt()) {
                            break;
                        }
                        int adminChoice = scanner.nextInt();

                        switch (adminChoice) {
                            case 1:
                                if (!admin.isLoggedIn()) {
                                    System.out.println("You must login first.");
                                    break;
                                }
                                System.out.print("Enter time to add: ");
                                int adminTime = scanner.nextInt();
                                service.addAvailableSlot(adminTime);
                                break;

                            case 2:
                                if (!admin.isLoggedIn()) {
                                    System.out.println("You must login first.");
                                    break;
                                }
                                service.viewAvailableSlots();
                                break;

                            case 3:
                                admin.logout();
                                break;

                            case 4:
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }

                        if (adminChoice == 4 || !admin.isLoggedIn()) {
                            break;
                        }
                    }
                    break;

                case 2:
                    while (true) {
                        System.out.println("\n=== User Menu ===");
                        System.out.println("1. Book Appointment");
                        System.out.println("2. Cancel Appointment");
                        System.out.println("3. View Schedule");
                        System.out.println("4. View Available Slots");
                        System.out.println("5. Modify Appointment");
                        System.out.println("6. Exit");
                        System.out.print("Choose an option: ");

                        if (!scanner.hasNextInt()) {
                            break;
                        }
                        int userChoice = scanner.nextInt();

                        switch (userChoice) {

                            case 1:
                                System.out.print("Enter user ID: ");
                                int id = scanner.nextInt();
                                scanner.nextLine();

                                System.out.print("Enter name: ");
                                String name = scanner.nextLine();

                                System.out.print("Enter participants: ");
                                int participants = scanner.nextInt();

                                User user = new User(id, name, participants);

                                System.out.print("Enter available time to book: ");
                                int time = scanner.nextInt();

                                Appointment appt = service.bookFromAvailableSlot(time, user);

                                if (appt != null) {
                                    System.out.println("Appointment booked!");

                                    if (!System.getProperty("testMode", "false").equals("true")) {
                                        emailService.sendEmail(
                                                ADMIN_EMAIL,
                                                "Appointment Confirmation",
                                                "Hello " + name + ",\n\nYour appointment has been booked successfully.\nTime: " + time + "\n\nThank you!"
                                        );
                                    }
                                } else {
                                    System.out.println("Booking failed. Time may not be available.");
                                }
                                break;

                            case 2:
                                System.out.print("Enter time to cancel: ");
                                int cancelTime = scanner.nextInt();

                                Appointment apptToCancel = service.findAppointmentByTime(cancelTime);

                                if (apptToCancel != null) {
                                    service.cancel(cancelTime);

                                    String userName = apptToCancel.getUser().getName();

                                    if (!System.getProperty("testMode", "false").equals("true")) {
                                        emailService.sendEmail(
                                                ADMIN_EMAIL,
                                                "Appointment Cancellation",
                                                "Hello " + userName + ",\n\nYour appointment at time "
                                                        + cancelTime + " has been canceled.\n\nThank you!"
                                        );
                                    }
                                } else {
                                    System.out.println("No appointment found at this time.");
                                }
                                break;

                            case 3:
                                service.printSchedule();
                                break;

                            case 4:
                                service.viewAvailableSlots();
                                break;

                            case 5:
                                System.out.print("Enter old time: ");
                                int oldTime = scanner.nextInt();

                                System.out.print("Enter new time: ");
                                int newTime = scanner.nextInt();

                                Appointment oldAppt = service.findAppointmentByTime(oldTime);

                                if (oldAppt != null) {
                                    String userName = oldAppt.getUser().getName();

                                    Appointment updated = service.modify(oldTime, newTime);

                                    if (updated != null) {
                                        if (!System.getProperty("testMode", "false").equals("true")) {
                                            emailService.sendEmail(
                                                    ADMIN_EMAIL,
                                                    "Appointment Updated",
                                                    "Hello " + userName + ",\n\nYour appointment has been updated.\nOld time: "
                                                            + oldTime + "\nNew time: " + newTime + "\n\nThank you!"
                                            );
                                        }
                                    }
                                } else {
                                    System.out.println("No appointment found at this time.");
                                }
                                break;

                            case 6:
                                break;

                            default:
                                System.out.println("Invalid choice.");
                        }

                        if (userChoice == 6) {
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}