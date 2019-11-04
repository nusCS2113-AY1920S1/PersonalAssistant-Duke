import chronologer.command.EditCommand;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
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
    private static Storage storage;

    @BeforeAll
    static void setup() {
        ArrayList<Task> testList = new ArrayList<Task>();
        tasks = new TaskList(testList);
        file = new File(System.getProperty("user.dir") + "/src/test/EditList");
        storage = new Storage(file);
    }

    /**
     * Test edit command basic functionality.
     */
    @Test
    void testEdit() throws ChronologerException {
        Task deadlineTest = new Deadline("ABC", LocalDateTime.of(2001, 1, 1, 1, 0));
        tasks.add(deadlineTest);

        EditCommand command = new EditCommand(0, "CDF");
        command.execute(tasks, storage);
        Assertions.assertEquals("CDF", deadlineTest.getDescription());
    }

    /**
     * Test invalid inputs for edit commands.
     */
    @Test
    void testError() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("edit");
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("edit -1 New description"));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("edit 1"));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            EditCommand test = new EditCommand(2, "Foxtrot");
            test.execute(tasks, storage);
        });

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