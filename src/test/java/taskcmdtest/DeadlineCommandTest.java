//@@author jessteoxizhi

package taskcmdtest;


import gazeeebo.ui.Ui;
import gazeeebo.commands.tasks.DeadlineCommand;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TriviaStorage;
import gazeeebo.tasks.Task;
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

class DeadlineCommandTest {
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
     * Test deadline line command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testDeadlineCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "deadline sleep/by 2019-12-12 12:03:03";
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        try {
            deadlineCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | DukeException | IOException e1) {
            e1.printStackTrace();
        }
        assertEquals("Got it. I've added this task:\r\n"
                + "[D][ND] sleep(by:12 Dec 2019 12:03:03)\r\n"
                + "Now you have 1 tasks in the list.\r\n", output.toString());
    }

    /**
     * Test invalid deadline command.
     * @throws IOException Exception when there is an error reading the triviaStorage
     */

    @Test
    void testWrongDeadlineCommand() throws IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.fullCommand = "deadline sleep/by 2019-12-2 12:03:03";
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> tasks = new ArrayList<Task>();
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        try {
            deadlineCommand.execute(tasks, ui, storage, commandStack, deletedTask,triviaManager);
        } catch (ParseException | DukeException | IOException e1) {
            e1.printStackTrace();
        }
        assertEquals("Date Time has to be in YYYY-MM-DD HH:mm:ss format\r\n", output.toString());
    }
}
