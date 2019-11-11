//@@author jessteoxizhi

package TaskCommandTest;

import gazeeebo.storage.TriviaStorage;
import gazeeebo.triviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.parser.TaskCommandParser;
import gazeeebo.commands.tasks.TodoCommand;
import gazeeebo.commands.tasks.UndoTaskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TasksPageStorage;
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

class UndoCommandTest extends UndoTaskCommand {
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
    void emptyCommandStackTest() throws IOException {
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        ArrayList<Task> list = new ArrayList<>();
        Storage storage = new Storage();
        undo(commandStack,list,storage);
        assertEquals("You cannot undo the previous command.\r\n", output.toString());
    }

    @Test
    void commandStackTest() throws IOException, ParseException, DukeException {
        Stack<ArrayList<Task>> commandStack = new Stack<>();
        TasksPageStorage tasksPageStorage = new TasksPageStorage();
        ArrayList<Task> list = tasksPageStorage.readFromSaveFile();
        Ui ui = new Ui();
        ArrayList<Task> oldList = new ArrayList<>();
        TaskCommandParser.copyOldList(oldList, list);
        commandStack.push(oldList);
        ui.fullCommand = "todo study";
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        ArrayList<Task> deletedTask = new ArrayList<>();
        TodoCommand todoCommand = new TodoCommand();
        Storage storage = new Storage();
        todoCommand.execute(list,ui,storage,commandStack,deletedTask, triviaManager);
        undo(commandStack,list,storage);
        assertEquals("Got it. I've added this task:\r\n"
                + "[T][ND] study\r\n"
                + "Now you have " + list.size() + " tasks in the list.\r\n"
                + "You have undo the previous command.\r\n", output.toString());
    }
}
