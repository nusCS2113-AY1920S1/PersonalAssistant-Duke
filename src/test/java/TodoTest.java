import javacake.DukeException;
import javacake.Parser;
import javacake.TaskList;
import javacake.Ui;
import javacake.Storage;
import javacake.commands.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
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
    void eat() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "todo eat";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Got it. I've added this task: \n   [T][✗] eat\nNow you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();

    }

    @Test
    void jog() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "todo jog";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Got it. I've added this task: \n   [T][✗] jog\nNow you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }

    @Test
    void todo() {
        Ui ui = new Ui();
        Storage storage = null;
        try {
            storage = new Storage("./data/test_data.txt");
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String input = "todo todo";
        setUpStreams();
        TaskList taskList = new TaskList();
        try {
            Command c = Parser.parse(input);
            c.execute(taskList, ui, storage);
        } catch (DukeException e) {
            ui.showMessage(e.getMessage());
        }
        String exp = "Got it. I've added this task: \n   [T][✗] todo\nNow you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }

}
