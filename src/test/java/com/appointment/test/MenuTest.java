package com.appointment.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.appointment.app.Menu;

public class MenuTest {

    @Test
    void testMainExit() {
        System.setProperty("testMode", "true");
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Are you Admin or User?"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testMainInvalidThenExit() {
        System.setProperty("testMode", "true");
        String input = "9\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Invalid choice."));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testAdminViewAvailableSlotsThenBackThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nadmin\n1234\n2\n4\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("=== Admin Menu ==="));
        assertTrue(result.contains("Available Slots"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testAdminAddSlotThenBackThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nadmin\n1234\n1\n10\n4\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Enter time to add:"));
        assertTrue(result.contains("Available slot added at time: 10"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testUserViewScheduleThenExit() {
        System.setProperty("testMode", "true");
        String input = "2\n3\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("=== User Menu ==="));
        assertTrue(result.contains("No appointments scheduled."));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testUserCancelThenExit() {
        System.setProperty("testMode", "true");
        String input = "2\n2\n10\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Enter time to cancel:"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testUserViewAvailableSlotsThenExit() {
        System.setProperty("testMode", "true");
        String input = "2\n4\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Available Slots"));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testAdminInvalidChoiceThenBackThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nadmin\n1234\n9\n4\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("=== Admin Menu ==="));
        assertTrue(result.contains("Invalid choice."));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testUserInvalidChoiceThenExit() {
        System.setProperty("testMode", "true");
        String input = "2\n9\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("=== User Menu ==="));
        assertTrue(result.contains("Invalid choice."));
        assertTrue(result.contains("Goodbye!"));
    }

    @Test
    void testAdminLoginSuccessThenLogoutThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nadmin\n1234\n3\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();

        assertTrue(result.contains("=== Admin Menu ==="));
    }

    @Test
    void testAdminLoginFailThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nwrong\n1111\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();

        assertTrue(result.contains("Are you Admin or User?"));
    }
    @Test
    void testUserModifyThenExit() {
        System.setProperty("testMode", "true");
        String input = "1\nadmin\n1234\n1\n10\n1\n11\n4\n2\n1\n1\nSara\n2\n10\n5\n10\n11\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        Menu.main(new String[]{});

        System.setOut(originalOut);

        String result = output.toString();
        assertTrue(result.contains("Appointment modified successfully!"));
    }
}