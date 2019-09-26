import javacake.Ui;
import javacake.Storage;
import javacake.Parser;
import javacake.TaskList;
import javacake.DukeException;
import javacake.commands.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
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
    void test() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "deadline test /by 0000";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Got it. I've added this task: \n   [D][✗] test (by: 0000)\nNow you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }

    @Test
    void examBy_Date() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "deadline exam /by 01/01/2019";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Got it. I've added this task: \n   [D]"
                + "[✗] exam (by: 01/01/2019)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }

}
