import duke.DukeException;
import duke.Parser;
import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewSchedulesTest {
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
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test_data.txt");
        setUpStreams();
        TaskList taskList = new TaskList();
        Command c = Parser.parse("deadline a /by 3pm 15 sept");
        c.execute(taskList, ui, storage);
        restoreStreams();
        setUpStreams();
        c = Parser.parse("viewschedule 15 sept");
        c.execute(taskList, ui, storage);
        String exp = "Here are your tasks for 15 Sep 2019\n"
                    + "1. [D][✗] a (by: 3pm 15 sept)";
        assertEquals(exp, outContent.toString().trim().replace("\r", ""));
        restoreStreams();
    }
}
