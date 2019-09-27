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

class ExitCommandTest {

    private Command testCommand = new ExitCommand();
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

    ExitCommandTest() throws DukeException {
    }

    @Test
    void testExecute() throws DukeException {
        testUi.showWelcome();
        try {
            testCommand.execute(testTaskList, testUi, testStorage);
        } catch (Exception e) {
            assertEquals("Exit Error: Storage Attempt Failed", e.getMessage());
        }
    }
}