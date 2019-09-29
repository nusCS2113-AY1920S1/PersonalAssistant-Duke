package command;

import exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchCommandTest {

    private Command testCommand = new SearchCommand("dummy", "placeholder");
    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "E | 0 | Sleeping | 01-01-1970 2200");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt");

    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    SearchCommandTest() throws DukeException {
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(systemOutput));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testFind() throws DukeException {
        testCommand = new SearchCommand("find", "Sleep");
        testCommand.execute(testTaskList, testUi, testStorage);
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }

    @Test
    void testSchedule() throws DukeException {
        testCommand = new SearchCommand("schedule", "01-01-1970");
        testCommand.execute(testTaskList, testUi, testStorage);
        assertEquals("Here's what the day looks like:\r\n"
                + "4. [E][N] Sleeping (At: 01-01-1970 2200)\r\n", systemOutput.toString());
    }
}