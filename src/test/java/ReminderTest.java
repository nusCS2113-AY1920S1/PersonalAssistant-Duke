import duke.DukeException;
import duke.Parser;
import duke.TaskList;
import duke.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReminderTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        outContent.reset();
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void test() throws DukeException {
        setUpStreams();
        TaskList taskList = new TaskList();
        Command c = Parser.parse("deadline a /by 0600 9/15/2019");
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        c = Parser.parse("deadline b /by 0500 9/15/2019");;
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        restoreStreams();
        setUpStreams();
        c = Parser.parse("reminder");
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        String exp = "Here are the upcoming Deadlines:\n1.[D][✗] b (by: 0500 9/15/2019)\n2.[D][✗] a (by: 0600 9/15/2019)";
        assertEquals(exp, outContent.toString().trim().replace("\r", ""));
        restoreStreams();
    }

}
