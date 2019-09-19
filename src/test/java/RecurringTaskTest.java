import duke.DukeException;
import duke.Parser;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecurringTaskTest {
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
    public void simpleTest() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "todo a /daily tomorrow";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        String exp = "Got it. I've added this task: \n   [R][d]"
                + "[✗] a (at:"
                + " "
                + date.toString()
                + ")\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }
}
