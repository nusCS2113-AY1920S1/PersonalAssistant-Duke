import chronologer.command.EditCommand;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Test class for edit commands.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
class EditCommandTest {
    private LocalDateTime testDate = LocalDateTime.of(2, 2, 2, 2, 2, 2, 2);
    private static TaskList tasks;
    private static File file;
    private static File placeholder;
    private static Storage storage;
    private static ChronologerStateList history;

    @BeforeAll
    static void setup() {
        ArrayList<Task> testList = new ArrayList<Task>();
        tasks = new TaskList(testList);
        file = new File(System.getProperty("user.dir") + "/src/test/EditList");
        placeholder = new File(System.getProperty("user.dir") + "/src/test/States");
        storage = new Storage(file);
        history = new ChronologerStateList(placeholder, placeholder, placeholder);
    }

    /**
     * Test edit command basic functionality.
     */
    @Test
    void testEdit() throws ChronologerException {
        Task deadlineTest = new Deadline("ABC", LocalDateTime.of(2001, 1, 1, 1, 0));
        tasks.add(deadlineTest);

        EditCommand command = new EditCommand(0, "CDF");
        command.execute(tasks, storage, history);
        Assertions.assertEquals("CDF", deadlineTest.getDescription());
    }

    /**
     * Test the task list edit function.
     */
    @Test
    void testTaskListEdit() {
        Deadline testTask = new Deadline("Minecraft", testDate);
        ArrayList<Task> test = new ArrayList<>();
        test.add(testTask);
        TaskList testList = new TaskList(test);
        testList.editTaskDescription(0, "Roblox");

        test = testList.getTasks();
        Assertions.assertEquals(test.get(0).getDescription(), "Roblox");
    }

    /**
     * Ensure that file is deleted to prevent repetition related errors and preserve space.
     */
    @AfterAll
    static void teardownSetup() {
        assert file.delete();
    }
}