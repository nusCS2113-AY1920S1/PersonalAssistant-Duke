//@@author e0323290

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.commands.tasks.CategoryListCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.UI.Ui;
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

/**
 * Testing CategoryListCommand when calling todo, deadline and event
 * lists, and nonexistent list error.
 */
public class CategoryListCommandTest {
    /**
     * Output stream in which data is written into a byte array.
     */
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    /**
     * Print representation of actual data values.
     */
    private PrintStream mine = new PrintStream(output);
    /**
     * Print representation of original data values.
     */
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

    /**
     * Test calling category todo list.
     *
     * @throws DukeException  Throws custom exception when
     *                        format of tag command is wrong
     * @throws ParseException Catch error if parsing of commands fails
     * @throws IOException    Catch error if reading of file fails
     */
    @Test
    void testTodoCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        ArrayList<Task> list = new ArrayList<>();
        ui.fullCommand = "todo list";
        Deadline dl1 = new Deadline("assignment", "2019-02-01 12:12:12");
        Deadline dl2 = new Deadline("essay", "2019-01-03 01:01:01");
        Event e1 = new Event("project meeting", "2018-09-09 07:00:00-09:00:00");
        Event e2 = new Event("gathering", "2019-09-09 12:00:00-13:00:00");
        Todo td1 = new Todo("go to lecture");
        Todo td2 = new Todo("do tutorials");
        list.add(dl1);
        list.add(dl2);
        list.add(e1);
        list.add(e2);
        list.add(td1);
        list.add(td2);

        final Storage storage = new Storage();
        final Stack<ArrayList<Task>> commandStack = new Stack<>();
        final ArrayList<Task> deletedTask = new ArrayList<>();
        final TriviaManager triviaManager = new TriviaManager(storage);
        CategoryListCommand catList = new CategoryListCommand();
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("List of todo tasks:\r\n"
                + "1.[T][ND] go to lecture\r\n"
                + "2.[T][ND] do tutorials\r\n", output.toString());
    }

    /**
     * Test calling category event list.
     *
     * @throws DukeException  Throws custom exception when
     *                        format of tag command is wrong
     * @throws ParseException Catch error if parsing of commands fails
     * @throws IOException    Catch error if reading of file fails
     */
    @Test
    void testEventCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        ArrayList<Task> list = new ArrayList<>();

        ui.fullCommand = "event list";
        Deadline dl1 = new Deadline("assignment", "2019-02-01 12:12:12");
        Deadline dl2 = new Deadline("essay", "2019-01-03 01:01:01");
        Event e1 = new Event("project meeting", "2018-09-09 07:00:00-09:00:00");
        Event e2 = new Event("gathering", "2019-09-09 12:00:00-13:00:00");
        Todo td1 = new Todo("go to lecture");
        Todo td2 = new Todo("do tutorials");
        list.add(dl1);
        list.add(dl2);
        list.add(e1);
        list.add(e2);
        list.add(td1);
        list.add(td2);
        Storage storage = new Storage();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<>();
        TriviaManager triviaManager = new TriviaManager(storage);
        CategoryListCommand catList = new CategoryListCommand();
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("List of events tasks:\r\n"
                + "1.[E][ND]project meeting"
                + "(at:09 Sep 2018 07:00:00-09:00:00)\r\n"
                + "2.[E][ND]gathering"
                + "(at:09 Sep 2019 12:00:00-13:00:00)\r\n", output.toString());
    }

    /**
     * Test calling category deadline list.
     *
     * @throws DukeException  Throws custom exception when
     *                        format of tag command is wrong
     * @throws ParseException Catch error if parsing of commands fails
     * @throws IOException    Catch error if reading of file fails
     */
    @Test
    void testDeadlineCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        ArrayList<Task> list = new ArrayList<>();

        ui.fullCommand = "deadline list";
        Deadline dl1 = new Deadline("assignment", "2019-02-01 12:12:12");
        Deadline dl2 = new Deadline("essay", "2019-01-03 01:00:00");
        Event e1 = new Event("project meeting", "2018-09-09 07:00:00-09:00:00");
        Event e2 = new Event("gathering", "2019-09-09 12:00:00-13:00:00");
        Todo td1 = new Todo("go to lecture");
        Todo td2 = new Todo("do tutorials");
        list.add(dl1);
        list.add(dl2);
        list.add(e1);
        list.add(e2);
        list.add(td1);
        list.add(td2);
        Storage storage = new Storage();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<>();
        TriviaManager triviaManager = new TriviaManager(storage);
        CategoryListCommand catList = new CategoryListCommand();
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("List of events tasks:\r\n"
                + "1.[D][ND]assignment"
                + "(by:01 Feb 2019 12:12:12)\r\n"
                + "2.[D][ND]essay"
                + "(by:03 Jan 2019 01:00:00)\r\n", output.toString());
    }

    /**
     * Test calling nonexistent category list.
     *
     * @throws DukeException  Throws custom exception when
     *                        format of tag command is wrong
     * @throws ParseException Catch error if parsing of commands fails
     * @throws IOException    Catch error if reading of file fails
     */
    @Test
    void testNoListCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        ArrayList<Task> list = new ArrayList<>();

        ui.fullCommand = "abc list";
        Deadline dl1 = new Deadline("assignment", "2019-02-01 12:12:12");
        Deadline dl2 = new Deadline("essay", "2019-01-03 01:00:00");
        Event e1 = new Event("project meeting", "2018-09-09 07:00:00-09:00:00");
        Event e2 = new Event("gathering", "2019-09-09 12:00:00-13:00:00");
        Todo td1 = new Todo("go to lecture");
        Todo td2 = new Todo("do tutorials");
        list.add(dl1);
        list.add(dl2);
        list.add(e1);
        list.add(e2);
        list.add(td1);
        list.add(td2);
        Storage storage = new Storage();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<>();
        TriviaManager triviaManager = new TriviaManager(storage);
        CategoryListCommand catList = new CategoryListCommand();
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("OOPS!!! I'm sorry, but I don't know what "
                + "that means :-(\r\n", output.toString());
    }
}

