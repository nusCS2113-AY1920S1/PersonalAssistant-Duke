//@@author JasonLeeWeiHern
package EditCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.edit.EditTimeCommand;
import gazeeebo.tasks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTimeTest {
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
    void testDeadlineEditTime() throws IOException {
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ByteArrayInputStream third = new ByteArrayInputStream("2020-12-12 11:11:11".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n"
                        + "Ok, we have edited your Deadline time."
                        + "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)"
                        + "\n\tTo:   [D][ND] tutorial homework(by:12 Dec 2020 11:11:11)\n",
                output.toString());
    }
    @Test
    void testEventEditTime() throws IOException {
        Event testE = new Event("trg", "2019-12-12 10:10:10-11:11:11");
        tasks.add(testE);
        ByteArrayInputStream third = new ByteArrayInputStream("2020-12-12 11:11:11-12:12:12".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n"
                        + "Ok, we have edited your Event time."
                        + "\n\tFrom: [E][ND]trg(at:12 Dec 2019 10:10:10-11:11:11)"
                        + "\n\tTo:   [E][ND]trg(at:12 Dec 2020 11:11:11-12:12:12)\n",
                output.toString());
    }
    @Test
    void testFixDurationEditTime() throws IOException {
        FixedDuration testF = new FixedDuration("trg", "2 hours");
        tasks.add(testF);
        ByteArrayInputStream third = new ByteArrayInputStream("3 hours".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n"
                        + "Ok, we have edited your FixDuration time."
                        + "\n\tFrom: [FD][ND] trg(requires:2 hours)"
                        + "\n\tTo:   [FD][ND] trg(requires:3 hours)\n",
                output.toString());
    }
    @Test
    void testTimeBoundEditTime() throws IOException {
        Timebound testT = new Timebound("trg", "2019-08-12 and 2019-08-13");
        tasks.add(testT);
        ByteArrayInputStream third = new ByteArrayInputStream("2019-08-12 and 2019-09-13".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n"
                        + "Ok, we have edited your Timebound time."
                        + "\n\tFrom: [P][ND] trg(between: 12 Aug 2019 and 13 Aug 2019)"
                        + "\n\tTo:   [P][ND] trg(between: 12 Aug 2019 and 13 Sep 2019)\n",
                output.toString());
    }
    @Test
    void testUnableToEditEditTime() throws IOException {
        DoAfter testT = new DoAfter("read book","read book","return book");
        tasks.add(testT);
        ByteArrayInputStream third = new ByteArrayInputStream("2019-08-12 and 2019-09-13".getBytes());
        System.setIn(third);
        EditTimeCommand test = new EditTimeCommand(tasks, ui, 0);
        assertEquals("Type your time:\n"
                + "That input has no time to be edited.\n",
                output.toString());
    }
}
