
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.commands.edit.EditBothCommand;
import gazeeebo.commands.edit.EditDescriptionCommand;
import gazeeebo.commands.edit.EditTimeCommand;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EditCommandTest {
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
    void testEditTime() throws IOException {
        Ui ui = new Ui();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ByteArrayInputStream third = new ByteArrayInputStream("2020-12-12 11:11:11".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n" +
                        "Ok, we have edited your Deadline time." +
                        "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)" +
                        "\n\tTo:   [D][ND] tutorial homework(by:12 Dec 2020 11:11:11)\n",
                output.toString());
    }

    @Test
    void testEditDescription() throws IOException {
        Ui ui = new Ui();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ByteArrayInputStream third = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(third);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n" +
                        "Ok, we have edited your Deadline description." +
                        "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)" +
                        "\n\tTo:   [D][ND] tutorial assignment(by:12 Dec 2019 10:10:10)\n",
                output.toString());
    }

    @Test
    void testBoth() throws IOException {
        Ui ui = new Ui();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Event testE = new Event("banner rev", "2019-12-12 10:10:10-11:00:00");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("basketball trg /at 2019-12-12 09:00:00-11:00:00".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n" +
                        "Ok, we have edited your Event description and time." +
                        "\n\tFrom: [E][ND]banner rev(at:12 Dec 2019 10:10:10-11:00:00)" +
                        "\n\tTo:   [E][ND]basketball trg (at:12 Dec 2019 09:00:00-11:00:00)\n",
                output.toString());
    }
}