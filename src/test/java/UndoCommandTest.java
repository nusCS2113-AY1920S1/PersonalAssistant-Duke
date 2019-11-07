import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.parsers.TaskCommandParser;
import gazeeebo.commands.tasks.TodoCommand;
import gazeeebo.commands.tasks.UndoTaskCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
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

class UndoCommandTest extends UndoTaskCommand{
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }
    @Test
    void EmptyCommandStackTest() throws IOException {
        Stack<ArrayList<Task>> CommandStack = new Stack<>();
        ArrayList<Task> list = new ArrayList<>();
        Storage storage = new Storage();
        undo(CommandStack,list,storage);
        assertEquals("You cannot undo the previous command.\r\n", output.toString());
    }
    @Test
    void CommandStackTest() throws IOException, ParseException, DukeException {
        Stack<ArrayList<Task>> CommandStack = new Stack<>();
        Storage storage = new Storage();
        ArrayList<Task> list = storage.readFromSaveFile();
        TodoCommand todoCommand = new TodoCommand();
        Ui ui = new Ui();
        ArrayList<Task> deletedTask = new ArrayList<>();
        TriviaManager triviaManager = new TriviaManager(storage);
        ArrayList<Task> oldList = new ArrayList<>();
        TaskCommandParser.copyOldList(oldList, list);
        CommandStack.push(oldList);
        ui.fullCommand = "todo study";
        todoCommand.execute(list,ui,storage,CommandStack,deletedTask, triviaManager);
        undo(CommandStack,list,storage);
        assertEquals("Got it. I've added this task:\r\n" +
                "[T][ND] study\r\n" +
                "Now you have " + list.size() + " tasks in the list.\r\n" +
                "You have undo the previous command.\r\n", output.toString());
    }
}
