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

class AddCommandTest {

    private Command testCommand = new AddCommand("todo", "Sleep");
    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "W | 0 | Sleeping | Jan 15th and 25th");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt");

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

    AddCommandTest() throws DukeException {
    }

    @Test
    void testAddEvent() throws DukeException {
        testCommand = new AddCommand("event", "Sleep /at 01-01-1970 2200");
        testCommand.execute(testTaskList, testUi, testStorage);
        testCommand = new AddCommand("event", "Work /at 01-01-1970 2200");
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testCommand.execute(testTaskList, testUi, testStorage);
        assertEquals("Got it. I've added this task:\n"
                + "  [E][N] Work (At: 01-01-1970 2200)\n"
                + "Now you have 6 tasks in the list.\r\n"
                + "There is a conflict in the schedule!\r\n", freshOutput.toString());
    }

    @Test
    void testExecute() throws DukeException {
        testCommand.execute(testTaskList, testUi, testStorage);
        assertEquals("[T][N] Sleep", testTaskList.get(4).toList());
    }
}