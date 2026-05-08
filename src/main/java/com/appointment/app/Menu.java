package com.appointment.app;

import java.util.Scanner;

import com.appointment.admin.Admin;
import com.appointment.model.Appointment;
import com.appointment.model.User;
import com.appointment.notification.RealEmailNotification;
import com.appointment.service.BookingService;

public class Menu {

    private static final String ADMIN_EMAIL = "sara7maher@gmail.com";
    private static final String TEST_MODE = "testMode";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BookingService service = new BookingService();

        RealEmailNotification emailService =
                new RealEmailNotification(
                        ADMIN_EMAIL,
                        "sqzmwcqvhasndbis"
                );

        Admin admin = new Admin();

        runMainMenu(scanner, service, emailService, admin);
    }

    private static void runMainMenu(
            Scanner scanner,
            BookingService service,
            RealEmailNotification emailService,
            Admin admin
    ) {

        while (true) {

            printMainMenu();

            if (!scanner.hasNextInt()) {
                break;
            }

            int role = scanner.nextInt();

            switch (role) {

                case 1:
                    handleAdminLogin(scanner, service, admin);
                    break;

                case 2:
                    handleUserMenu(scanner, service, emailService);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMainMenu() {

        System.out.println("\nAre you Admin or User?");
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleAdminLogin(
            Scanner scanner,
            BookingService service,
            Admin admin
    ) {

        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        if (!admin.login(username, password)) {
            return;
        }

        handleAdminMenu(scanner, service, admin);
    }

    private static void handleAdminMenu(
            Scanner scanner,
            BookingService service,
            Admin admin
    ) {

        while (true) {

            printAdminMenu();

            if (!scanner.hasNextInt()) {
                break;
            }

            int adminChoice = scanner.nextInt();

            switch (adminChoice) {

                case 1:
                    addAvailableSlot(scanner, service, admin);
                    break;

                case 2:
                    viewAvailableSlots(service, admin);
                    break;

                case 3:
                    admin.logout();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }

            if (!admin.isLoggedIn()) {
                break;
            }
        }
    }

    private static void printAdminMenu() {

        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Add Available Slot");
        System.out.println("2. View Available Slots");
        System.out.println("3. Logout");
        System.out.println("4. Back");
        System.out.print("Choose an option: ");
    }

    private static void addAvailableSlot(
            Scanner scanner,
            BookingService service,
            Admin admin
    ) {

        if (!admin.isLoggedIn()) {
            System.out.println("You must login first.");
            return;
        }

        System.out.print("Enter time to add: ");

        int time = scanner.nextInt();

        service.addAvailableSlot(time);
    }

    private static void viewAvailableSlots(
            BookingService service,
            Admin admin
    ) {

        if (!admin.isLoggedIn()) {
            System.out.println("You must login first.");
            return;
        }

        service.viewAvailableSlots();
    }

    private static void handleUserMenu(
            Scanner scanner,
            BookingService service,
            RealEmailNotification emailService
    ) {

        while (true) {

            printUserMenu();

            if (!scanner.hasNextInt()) {
                break;
            }

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    bookAppointment(scanner, service, emailService);
                    break;

                case 2:
                    cancelAppointment(scanner, service, emailService);
                    break;

                case 3:
                    service.printSchedule();
                    break;

                case 4:
                    service.viewAvailableSlots();
                    break;

                case 5:
                    modifyAppointment(scanner, service, emailService);
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void printUserMenu() {

        System.out.println("\n=== User Menu ===");
        System.out.println("1. Book Appointment");
        System.out.println("2. Cancel Appointment");
        System.out.println("3. View Schedule");
        System.out.println("4. View Available Slots");
        System.out.println("5. Modify Appointment");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private static void bookAppointment(
            Scanner scanner,
            BookingService service,
            RealEmailNotification emailService
    ) {

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

        Appointment appointment =
                service.bookFromAvailableSlot(time, user);

        if (appointment == null) {

            System.out.println(
                    "Booking failed. Time may not be available."
            );

            return;
        }

        System.out.println("Appointment booked!");

        sendConfirmationEmail(emailService, name, time);
    }

    private static void cancelAppointment(
            Scanner scanner,
            BookingService service,
            RealEmailNotification emailService
    ) {

        System.out.print("Enter time to cancel: ");

        int cancelTime = scanner.nextInt();

        Appointment appointment =
                service.findAppointmentByTime(cancelTime);

        if (appointment == null) {

            System.out.println(
                    "No appointment found at this time."
            );

            return;
        }

        service.cancel(cancelTime);

        String userName =
                appointment.getUser().getName();

        sendCancellationEmail(
                emailService,
                userName,
                cancelTime
        );
    }

    private static void modifyAppointment(
            Scanner scanner,
            BookingService service,
            RealEmailNotification emailService
    ) {

        System.out.print("Enter old time: ");
        int oldTime = scanner.nextInt();

        System.out.print("Enter new time: ");
        int newTime = scanner.nextInt();

        Appointment oldAppointment =
                service.findAppointmentByTime(oldTime);

        if (oldAppointment == null) {

            System.out.println(
                    "No appointment found at this time."
            );

            return;
        }

        Appointment updated =
                service.modify(oldTime, newTime);

        if (updated == null) {

            System.out.println(
                    "Modification failed."
            );

            return;
        }

        String userName =
                oldAppointment.getUser().getName();

        sendUpdateEmail(
                emailService,
                userName,
                oldTime,
                newTime
        );
    }

    private static void sendConfirmationEmail(
            RealEmailNotification emailService,
            String name,
            int time
    ) {

        if (isTestMode()) {
            return;
        }

        emailService.sendEmail(
                ADMIN_EMAIL,
                "Appointment Confirmation",
                "Hello " + name
                        + ",\n\nYour appointment has been booked successfully.\nTime: "
                        + time
                        + "\n\nThank you!"
        );
    }

    private static void sendCancellationEmail(
            RealEmailNotification emailService,
            String userName,
            int cancelTime
    ) {

        if (isTestMode()) {
            return;
        }

        emailService.sendEmail(
                ADMIN_EMAIL,
                "Appointment Cancellation",
                "Hello " + userName
                        + ",\n\nYour appointment at time "
                        + cancelTime
                        + " has been canceled.\n\nThank you!"
        );
    }

    private static void sendUpdateEmail(
            RealEmailNotification emailService,
            String userName,
            int oldTime,
            int newTime
    ) {

        if (isTestMode()) {
            return;
        }

        emailService.sendEmail(
                ADMIN_EMAIL,
                "Appointment Updated",
                "Hello " + userName
                        + ",\n\nYour appointment has been updated.\nOld time: "
                        + oldTime
                        + "\nNew time: "
                        + newTime
                        + "\n\nThank you!"
        );
    }

    private static boolean isTestMode() {

        return System.getProperty(
                TEST_MODE,
                "false"
        ).equals("true");
    }
}