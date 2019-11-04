package EditCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.edit.EditBothCommand;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditBothTest {
    private Ui ui = new Ui();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void testBoth() throws IOException {
        Event testE = new Event("banner rev", "2019-12-12 10:10:10-11:00:00");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("basketball trg /at 2019-12-12 09:00:00-11:00:00".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n"
                        + "Ok, we have edited your Event description and time."
                        + "\n\tFrom: [E][ND]banner rev(at:12 Dec 2019 10:10:10-11:00:00)"
                        + "\n\tTo:   [E][ND]basketball trg (at:12 Dec 2019 09:00:00-11:00:00)\n",
                output.toString());
    }

}
