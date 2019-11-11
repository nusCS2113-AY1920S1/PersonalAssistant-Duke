//@@author jessteoxizhi

package taskcmdtest;


import gazeeebo.ui.Ui;
import gazeeebo.commands.tasks.FindCommand;
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

class FindCommandTest {
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
     * Test of find command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testFindCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "find chemistry homework";
        FindCommand findCommand = new FindCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            findCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("Here are the matching tasks in your list:\r\n"
                + "1.[T][ND] chemistry homework\r\n", output.toString());
    }

    /**
     * Test of invalid find command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testWrongFindCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "find ";
        FindCommand findCommand = new FindCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            findCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e1) {
            e1.printStackTrace();
        }
        assertEquals("OOPS!!! The description of a search cannot be empty.\r\n", output.toString());
    }
}
