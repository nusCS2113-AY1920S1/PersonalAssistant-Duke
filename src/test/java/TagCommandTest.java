//@@author e0323290
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.TagCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagCommandTest {
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
    void testTagCommandTest() throws DukeException,
            ParseException, IOException {
        //start time of clashing events are the same
        Ui ui = new Ui();
        Storage storage = new Storage();
        TriviaManager triviaManager = new TriviaManager(storage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();

        ui.fullCommand = "#school";
        TagCommand tc = new TagCommand();
        Event e = new Event("meeting #school", "2019-12-12 03:03:03-04:04:04");
        Todo td = new Todo("go to lecture #school");
        Deadline dl = new Deadline("project #school", "2019-03-04 12:12:12");
        tasks.add(e);
        tasks.add(td);
        tasks.add(dl);
        tc.execute(tasks, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("Here are the matching tags in your list:\r\n"
                + "1.[E][ND]meeting #school"
                + "(at:12 Dec 2019 03:03:03-04:04:04)\r\n"
                + "2.[T][ND] go to lecture #school\r\n"
                + "3.[D][ND] project #school"
                + "(by:04 Mar 2019 12:12:12)\r\n", output.toString());
    }


}
