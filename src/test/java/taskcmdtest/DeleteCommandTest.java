//@@author jessteoxizhi

package taskcmdtest;


import gazeeebo.ui.Ui;
import gazeeebo.commands.tasks.DeleteCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gazeeebo.triviamanager.TriviaManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCommandTest {
    //creating a stream to hold the output
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    //saving the original System.out
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        //tell java to print to my own stream
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    /**
     * Test of invalid delete command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testWrongDeleteCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "delete chemistry homework";
        DeleteCommand deleteCommand = new DeleteCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            deleteCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("Wrong input for delete command\r\n", output.toString());
    }

    /**
     * Test of delete command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testDeleteCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "delete 1";
        DeleteCommand deleteCommand = new DeleteCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            deleteCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("Noted. I've removed this task: \r\n"
                + "[T][ND] chemistry homework\r\n"
                + "Now you have 0 tasks in the list.\r\n", output.toString());
    }

    /**
     * Test of empty delete command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testEmptyDeleteCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "delete";
        DeleteCommand deleteCommand = new DeleteCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            deleteCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("OOPS!!! The description of a deletion cannot be empty.\r\n", output.toString());
    }

    /**
     * Test of delete all command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testDeleteAllCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "delete all";
        DeleteCommand deleteCommand = new DeleteCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            deleteCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("Noted. I've removed all the tasks.\r\n"
                + "Now you have 0 tasks in the list.\r\n", output.toString());
    }
}
