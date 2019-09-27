package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UITest {

    UI testUi = new UI();
    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(systemOutput));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testShowWelcome() {
        testUi.showWelcome();
        assertEquals("Hello! I'm Duke\n"
                + "What can I do for you?\n\r\n", systemOutput.toString());
    }

    /*
    @Test
    void testReadCommand() {
        String buffer = "";
        testUi.showWelcome();
        systemOutput.reset();
    }
    */

    @Test
    void testShowLine() {
        String buffer = testUi.showLine();
        assertEquals("____________________________________________________________", buffer);
        assertEquals("_________________________________________"
                + "___________________\r\n", systemOutput.toString());
    }

    @Test
    void testShowError() {
        testUi.showError("This is a test of the error message.");
        assertEquals("This is a test of the error message.\r\n", systemOutput.toString());
    }

    @Test
    void testShowLoadingError() {
        testUi.showLoadingError();
        assertEquals("Formatting Issues Encountered. New Task List initialized\r\n", systemOutput.toString());
    }

    @Test
    void testHastaLaVista() {
        testUi.hastaLaVista();
        assertEquals("Bye. Hope to see you again soon!\r\n", systemOutput.toString());
    }

    @Test
    void testCloseSuccess() {
        testUi.closeSuccess();
        assertEquals("File successfully saved!\r\n", systemOutput.toString());
    }

    @Test
    void testCloseFailure() {
        testUi.closeFailure();
        assertEquals("File failed to save\r\n", systemOutput.toString());
    }
}