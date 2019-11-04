package EditCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.edit.EditBothCommand;
import gazeeebo.tasks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Time;
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
    void testEventBoth() throws IOException {
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

    @Test
    void testDeadlineBoth() throws IOException {
        Deadline testE = new Deadline("banner rev", "2019-12-12 10:10:10");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("assignment /by 2019-12-12 09:00:00".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n"
                        + "Ok, we have edited your Deadline description and time."
                        + "\n\tFrom: [D][ND] banner rev(by:12 Dec 2019 10:10:10)"
                        + "\n\tTo:   [D][ND] assignment (by:12 Dec 2019 09:00:00)\n",
                output.toString());
    }

    @Test
    void testFixDurationBoth() throws IOException {
        FixedDuration testE = new FixedDuration("banner rev", "1 hour");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("assignment /require 2 hours".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n"
                        + "Ok, we have edited your FixDuration description and time."
                        + "\n\tFrom: [FD][ND] banner rev(requires:1 hour)"
                        + "\n\tTo:   [FD][ND] assignment(requires:2 hours)\n",
                output.toString());
    }
    @Test
    void testTimeboundBoth() throws IOException {
        Timebound testE = new Timebound("banner rev", "2019-08-12 and 2019-08-13");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("assignment /between 2019-08-12 and 2019-09-13".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n"
                        + "Ok, we have edited your Timebound description and time."
                        + "\n\tFrom: [P][ND] banner rev(between: 12 Aug 2019 and 13 Aug 2019)"
                        + "\n\tTo:   [P][ND] assignment(between: 12 Aug 2019 and 13 Sep 2019)\n",
                output.toString());
    }
    @Test
    void testNotAbleToEditBoth() throws IOException {
        DoAfter testE = new DoAfter("read book","read book","return book");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("assignment /between 2019-08-12 and 2019-09-13".getBytes());
        System.setIn(third);
        EditBothCommand test = new EditBothCommand(tasks, ui, 0);
        assertEquals("Type your description & date:\n"
                        + "That input has "
                + "no time/description to be edited.\n",
                output.toString());
    }

}
