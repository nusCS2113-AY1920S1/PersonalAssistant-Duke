import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
    public void testUndo() throws ChronologerException {
        testCore.add(testUndoableTask);
        history.addState(testCore.getTasks());
        testCore.updateListOfTasks(history.undo());
        Assertions.assertEquals(0, testCore.getTasks().size());
    }
}