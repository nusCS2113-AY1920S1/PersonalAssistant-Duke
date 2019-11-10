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


public class CategoryListCommandTest  {
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
    void testTodoCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        CategoryListCommand testC = new CategoryListCommand();
        ArrayList<Task> list = new ArrayList<>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        TriviaManager triviaManager = new TriviaManager(storage);

        ui.fullCommand = "todo list";
        CategoryListCommand catList = new CategoryListCommand();
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
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("List of todo tasks:\r\n"
        + "1.[T][ND] go to lecture\r\n"
        + "2.[T][ND] do tutorials\r\n", output.toString());
    }
    @Test
    void testDeadlineCategoryTest() throws ParseException,
            IOException, DukeException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        CategoryListCommand testC = new CategoryListCommand();
        ArrayList<Task> list = new ArrayList<>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        TriviaManager triviaManager = new TriviaManager(storage);

        ui.fullCommand = "event list";
        CategoryListCommand catList = new CategoryListCommand();
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
        catList.execute(list, ui, storage, commandStack,
                deletedTask, triviaManager);

        assertEquals("List of events tasks:\r\n"
                + "1.[E][ND]project meeting"
                + "(at:09 Sep 2018 07:00:00-09:00:00)\r\n"
                + "2.[E][ND]gathering"
                + "(at:09 Sep 2019 12:00:00-13:00:00)\r\n", output.toString());
    }
}

