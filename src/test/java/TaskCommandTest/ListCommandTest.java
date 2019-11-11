//@@author jessteoxizhi

package TaskCommandTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.ListCommand;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gazeeebo.triviaManager.TriviaManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCommandTest {
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
     * Test of list command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testListCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "list";
        ListCommand listCommand = new ListCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Todo todo = new Todo("chemistry homework");
        tasks.add(todo);
        try {
            listCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException e1) {
            e1.printStackTrace();
        }
        assertEquals("Here are the tasks in your list:\r\n"
                + "1.[T][ND] chemistry homework\r\n"
                + "Task progressive: __________________________________________________(0%)\r\n", output.toString());
    }
}
