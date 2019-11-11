import chronologer.command.Command;
import chronologer.command.RemindCommand;
import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import chronologer.parser.ParserFactory;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import chronologer.task.Event;
import chronologer.task.Deadline;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RemindCommandTest {

    private static ArrayList<Task> list;
    private static TaskList tasks;
    private static File file;
    private static File placeholder;
    private static Storage storage;
    private static Command reminder;
    private static ChronologerStateList history;

    /**
     * Setups the necessary base to carry out the test operations.
     */
    @BeforeAll
    public static void setup() {
        list = new ArrayList<>();
        tasks = new TaskList(list);
        file = new File(System.getProperty("user.dir") + "/src/test/ArrayList");
        placeholder = new File(System.getProperty("user.dir") + "/src/test/States");
        storage = new Storage(file);
        history = new ChronologerStateList(placeholder, placeholder, placeholder);
        reminder = new RemindCommand(1, 5);

        Todo filler = new Todo("1");
        tasks.add(filler);
    }

    @Test
    public void testReminder() throws ChronologerException {
        Task testTask = new Deadline("test", LocalDateTime.of(2019, 8, 1, 12, 0));
        tasks.add(testTask);
        reminder.execute(tasks, storage, history);
        Assertions.assertNotNull(testTask.getReminder());
        Assertions.assertTrue(testTask.isReminderTrigger());
    }

    @Test
    public void testReminderNotTriggered() throws ChronologerException {
        Task testTask = new Event("test",
                LocalDateTime.of(3019, 8, 1, 12, 0),
                LocalDateTime.of(3019, 8, 2, 12, 0));
        list.add(testTask);
        TaskList tasks = new TaskList(list);
        reminder.execute(tasks, storage, history);
        Assertions.assertNotNull(testTask.getReminder());
        Assertions.assertFalse(testTask.isReminderTrigger());
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse("remind");
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            ParserFactory.parse(("remind 0 in 3 days"));
        });
        Assertions.assertThrows(ChronologerException.class, () -> {
            Command test = new RemindCommand(100, 3);
            test.execute(tasks, storage, history);
        });
    }

    @AfterAll
    public static void teardownSetup() {
        assert file.delete();
    }
}
