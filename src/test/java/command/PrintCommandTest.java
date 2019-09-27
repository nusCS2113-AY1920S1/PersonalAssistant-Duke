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

class PrintCommandTest {

    private Command testCommand = new PrintCommand();
    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "W | 0 | Sleeping | Jan 15th and 25th");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt");

    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    PrintCommandTest() throws DukeException {
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
    void testExecute() throws DukeException {
        testCommand.execute(testTaskList, testUi, testStorage);
        assertEquals("1. [T][N] Send even more Help\r\n"
                + "2. [R][N] Deliver Help (Every: Day)\r\n"
                + "3. [A][N] Send less help (After: Sending Enough)\r\n"
                + "4. [W][N] Sleeping (Between: Jan 15th and 25th)\r\n", systemOutput.toString());
    }
}