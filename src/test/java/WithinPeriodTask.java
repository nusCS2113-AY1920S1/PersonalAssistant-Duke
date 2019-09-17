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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithinPeriodTask {
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
    public void test() throws DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test_data.txt");
        String input = "todo completing my work within my house /within 1 sep to 2 dec";
        setUpStreams();
        TaskList taskList = new TaskList();
        Command c = Parser.parse(input);
        c.execute(taskList, ui, storage);
        String exp = "Got it. I've added this task: \n   "
                + "[T][✗] completing my work within my house within 1 sep to 2 dec\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(exp, outContent.toString().trim());
        restoreStreams();
    }
}
