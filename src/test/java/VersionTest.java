import chronologer.command.Command;
import chronologer.command.StoreVersionCommand;
import chronologer.command.RestoreVersionCommand;
import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import chronologer.task.Task;

import java.io.File;
import java.util.ArrayList;

/**
 * Tests version storing and restoring functionality.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
class VersionTest {
    private static Storage storage;
    private static File file;
    private static File v1;
    private static File v2;
    private static File v3;
    private static ChronologerStateList history;
    private static ArrayList<Task> testStore;
    private static ArrayList<Task> testCurrent;
    private static ArrayList<Task> testRestore;
    private static TaskList testVersionCommand;
    private static ArrayList<Task> testVersion;
    private static Todo testVersionTask;


    @BeforeAll
    static void setup() {
        testStore = new ArrayList<Task>();
        testCurrent = new ArrayList<Task>();
        testRestore = new ArrayList<Task>();
        testVersionTask = new Todo("testVersion");
        testVersion = new ArrayList<Task>();

        testStore.add(testVersionTask);

        testRestore.add(testVersionTask);
        testRestore.add(testVersionTask);

        testVersionCommand = new TaskList(testVersion);

        file = new File(System.getProperty("user.dir") + "/src/test/Test");

        v1 = new File(System.getProperty("user.dir") + "/src/test/v1");
        v2 = new File(System.getProperty("user.dir") + "/src/test/v2");
        v3 = new File(System.getProperty("user.dir") + "/src/test/v3");

        history = new ChronologerStateList(v1, v2, v3);
        storage = new Storage(file);
    }

    @Test
    public void testStoreVersion() throws ChronologerException {
        history.storeVersion(testStore,1);
        testCurrent = history.restoreVersion(testCurrent,1);
        Assertions.assertEquals(1, testCurrent.size());
    }

    @Test
    public void testRestoreVersion() throws ChronologerException {
        history.storeVersion(testRestore,2);
        testCurrent = history.restoreVersion(testCurrent,2);
        Assertions.assertEquals(2, testCurrent.size());
    }

    @Test
    public void testStoreVersionCommand() throws ChronologerException {
        Command store = new StoreVersionCommand(1);
        store.execute(testVersionCommand, storage, history);
        testVersionCommand.updateListOfTasks(history.restoreVersion(testVersionCommand.getTasks(),1));
        Assertions.assertEquals(0, testVersionCommand.getSize());
    }

    @Test
    public void testRestoreVersionCommand() throws ChronologerException {
        Command restore = new RestoreVersionCommand(2);
        restore.execute(testVersionCommand, storage, history);
        Assertions.assertEquals(2, testVersionCommand.getSize());
    }

    @AfterAll
    static void teardownSetup() {
        assert v1.delete();
        assert v2.delete();
    }
}
