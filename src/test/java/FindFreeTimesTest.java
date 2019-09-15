import duke.DukeException;
import duke.Parser;
import duke.TaskList;
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

public class FindFreeTimesTest {
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
        Command c = Parser.parse("event a /at 15 sept 12pm");
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        restoreStreams();
        setUpStreams();
        c = Parser.parse("freetime 6");
        Date currDate = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date newDate = calendar.getTime();
        c.execute(taskList, DukeTest.ui, DukeTest.storage);
        String exp = "Current Date: " + currDate.toString() + "\nNext Available: " + newDate.toString();
        assertEquals(exp, outContent.toString().trim().replace("\r", ""));
        restoreStreams();
    }
}
