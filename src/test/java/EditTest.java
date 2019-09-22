import duke.Ui;
import duke.Storage;
import duke.Parser;
import duke.TaskList;
import duke.DukeException;
import duke.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTest {
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
    void editDeadline() throws DukeException {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse("deadline assignment /by 9/15/2019");
            c.execute(taskList, ui, storage);
            restoreStreams();
            setUpStreams();
            c = Parser.parse("edit 1 12/12/2012");
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Noted. I've rescheduled this task: \n" +
                "[D][✗] assignment (by: 12/12/2012)";
        assertEquals(exp, outContent.toString().trim().replace("\r", ""));
        restoreStreams();
    }


    @Test
    void editBirthday() throws DukeException {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse("event birthday /at 9/15/2019");
            c.execute(taskList, ui, storage);
            restoreStreams();
            setUpStreams();
            c = Parser.parse("edit 1 12/12/2012");
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Noted. I've rescheduled this task: \n" +
                "[E][✗] birthday (at: 12/12/2012)";
        assertEquals(exp, outContent.toString().trim().replace("\r", ""));
        restoreStreams();
    }
}
