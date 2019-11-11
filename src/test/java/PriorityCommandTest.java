import chronologer.command.PriorityCommand;
import chronologer.exception.ChronologerException;
import chronologer.parser.ParserFactory;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Priority;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Test class for priority commands.
 * @author Tan Yi Xiang
 * @version V1.0
 */
class PriorityCommandTest {

    private static TaskList tasks;
    private static File file;
    private static Storage storage;
    private static ChronologerStateList history;

    @BeforeAll
    static void setup() {
        ArrayList<Task> testList = new ArrayList<Task>();
        tasks = new TaskList(testList);
        file = new File(System.getProperty("user.dir") + "/src/test/PriorityList");
        File placeholder = new File(System.getProperty("user.dir") + "/src/test/States");
        storage = new Storage(file);
        history = new ChronologerStateList(placeholder, placeholder, placeholder);
    }

    /**
     * Test whether priority function works for corresponding strings regardless of cases.
     * Also test default priority value.
     *
     */
    @Test
    void testPriority() throws ChronologerException {
        Task deadlineTest = new Deadline("ABC", LocalDateTime.of(2001, 1, 1, 1, 0));
        tasks.add(deadlineTest);
        Assertions.assertEquals(deadlineTest.getPriority(), Priority.MEDIUM);

        PriorityCommand command = new PriorityCommand(0, "HIGH");
        command.execute(tasks, storage, history);
        Assertions.assertEquals(deadlineTest.getPriority(), Priority.HIGH);

        command = new PriorityCommand(0, "MedIum");
        command.execute(tasks, storage, history);
        Assertions.assertEquals(deadlineTest.getPriority(), Priority.MEDIUM);

        command = new PriorityCommand(0, "low");
        command.execute(tasks, storage, history);
        Assertions.assertEquals(deadlineTest.getPriority(), Priority.LOW);
    }

    /**
     * Test invalid inputs for priority related commands.
     *
     */
    @Test
    void testInvalidPriority() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            PriorityCommand test = new PriorityCommand(0, "Gibberish");
            test.execute(tasks, storage, history);
        });
    }

    /**
     * Ensure that file is deleted to prevent repetition related errors and preserve space.
     */
    @AfterAll
    static void teardownSetup() {
        assert file.delete();
    }

}
