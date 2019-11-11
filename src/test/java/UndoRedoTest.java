import chronologer.command.Command;
import chronologer.command.RedoCommand;
import chronologer.command.ThemeCommand;
import chronologer.command.UndoCommand;
import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import chronologer.ui.UiMessageHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import chronologer.task.Task;

import java.io.File;
import java.util.ArrayList;

/**
 * Tests undo/redo functionality.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
class UndoRedoTest {
    private static Storage storage;
    private static File file;
    private static File v1;
    private static File v2;
    private static File v3;
    private static ChronologerStateList history;
    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static Todo testUndoableTask;

    @BeforeAll
    static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);

        testUndoableTask = new Todo("testVersion");

        file = new File(System.getProperty("user.dir") + "/src/test/Test");

        v1 = new File(System.getProperty("user.dir") + "/src/test/v1");
        v2 = new File(System.getProperty("user.dir") + "/src/test/v2");
        v3 = new File(System.getProperty("user.dir") + "/src/test/v3");

        history = new ChronologerStateList(v1, v2, v3);
        history.addState(testCore.getTasks());
        storage = new Storage(file);
    }

    @Test
    @Order(1)
    public void testRedo() throws ChronologerException {
        testCore.add(testUndoableTask);
        history.addState(testCore.getTasks());
        testCore.updateListOfTasks(history.undo());
        testCore.updateListOfTasks(history.redo());
        Assertions.assertEquals(1, testCore.getTasks().size());

        // Revert to original state.
        testCore.updateListOfTasks(history.undo());
    }

    @Test
    @Order(2)
    public void testUndo() throws ChronologerException {
        testCore.add(testUndoableTask);
        history.addState(testCore.getTasks());
        testCore.updateListOfTasks(history.undo());
        Assertions.assertEquals(0, testCore.getTasks().size());
    }

    @Test
    @Order(3)
    public void testRedoCommand() throws ChronologerException {
        Command redo = new RedoCommand();
        redo.execute(testCore, storage, history);
        Assertions.assertEquals("Redo successful!", UiMessageHandler.getOutputForGui());
    }

    @Test
    @Order(4)
    public void testUndoCommand() throws ChronologerException {
        Command undo = new UndoCommand();
        undo.execute(testCore, storage, history);
        Assertions.assertEquals("Undo successful!", UiMessageHandler.getOutputForGui());
    }

    @AfterAll
    static void teardownSetup() {
        assert file.delete();
    }

}