import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.tasks.UndoCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gazeeebo.exception.DukeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoCommandTest {
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
    public void EmptyCommandStackTest() {
        UndoCommand undoCommand = new UndoCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Stack<String> CommandStack = new Stack<>();
        ArrayList<Task> deletedTask = new ArrayList<Task>();
        Storage storage = new Storage();
        TriviaManager triviaManager = new TriviaManager();
        try {
            undoCommand.execute(tasks, ui, storage, CommandStack, deletedTask,triviaManager);
        } catch (ParseException | IOException | DukeException e) {
            e.printStackTrace();
        }
        assertEquals("The previous command cannot be undo\r\n", output.toString());
    }
}
