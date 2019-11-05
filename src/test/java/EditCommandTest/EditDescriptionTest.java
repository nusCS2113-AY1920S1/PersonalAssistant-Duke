package EditCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.edit.EditDescriptionCommand;
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

public class EditDescriptionTest {
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
    void testEditTodoDescription() throws IOException {
        Todo testT = new Todo("tutorial homework");
        tasks.add(testT);
        ByteArrayInputStream in = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your ToDo description."
                        + "\n\tFrom: [T][ND] tutorial homework"
                        + "\n\tTo:   [T][ND] tutorial assignment\n",
                output.toString());
    }

    @Test
    void testEditDeadlineDescription() throws IOException {
        Deadline testd = new Deadline("tutorial homework", "2019-12-12 10:10:10");
        tasks.add(testd);
        ByteArrayInputStream in = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your Deadline description."
                        + "\n\tFrom: [D][ND] tutorial homework(by:12 Dec 2019 10:10:10)"
                        + "\n\tTo:   [D][ND] tutorial assignment(by:12 Dec 2019 10:10:10)\n",
                output.toString());
    }

    @Test
    void testEditEventDescription() throws IOException {
        Event testE = new Event("Career Talk", "2019-12-12 10:10:10-11:11:11");
        tasks.add(testE);
        ByteArrayInputStream in = new ByteArrayInputStream("Career GE Talk".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your Event description."
                        + "\n\tFrom: [E][ND]Career Talk(at:12 Dec 2019 10:10:10-11:11:11)"
                        + "\n\tTo:   [E][ND]Career GE Talk(at:12 Dec 2019 10:10:10-11:11:11)\n",
                output.toString());
    }

    @Test
    void testEditDoAfterDescription() throws IOException {
        DoAfter testd = new DoAfter("brush teeth", "brush teeth", "sleep");
        tasks.add(testd);
        ByteArrayInputStream in = new ByteArrayInputStream("bathe /after brush teeth".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your DoAfter description."
                        + "\n\tFrom: [DA][ND] sleep(/after:brush teeth)"
                        + "\n\tTo:   [DA][ND] bathe(/after:brush teeth)\n",
                output.toString());
    }

    @Test
    void testEditFixDurationDescription() throws IOException {
        FixedDuration testF = new FixedDuration("tutorial homework", "2 hours");
        tasks.add(testF);
        ByteArrayInputStream in = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your FixedDuration description."
                        + "\n\tFrom: [FD][ND] tutorial homework(requires:2 hours)"
                        + "\n\tTo:   [FD][ND] tutorial assignment(requires:2 hours)\n",
                output.toString());
    }

    @Test
    void testEditTimeBoundDescription() throws IOException {
        Timebound testT = new Timebound("tutorial homework", "2019-10-10 and 2019-11-10");
        tasks.add(testT);
        ByteArrayInputStream in = new ByteArrayInputStream("tutorial assignment".getBytes());
        System.setIn(in);
        EditDescriptionCommand test = new EditDescriptionCommand(tasks, ui, 0);
        assertEquals("Type your description:\n"
                        + "Ok, we have edited your Timebound description."
                        + "\n\tFrom: [P][ND] tutorial homework(between: 10 Oct 2019 and 10 Nov 2019)"
                        + "\n\tTo:   [P][ND] tutorial assignment(between: 10 Oct 2019 and 10 Nov 2019)\n",
                output.toString());
    }
}
