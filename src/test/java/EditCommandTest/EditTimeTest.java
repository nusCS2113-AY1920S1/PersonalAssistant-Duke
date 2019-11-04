package EditCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.edit.EditTimeCommand;
import gazeeebo.tasks.Deadline;
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
    void testEditTime() throws IOException {
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
}
