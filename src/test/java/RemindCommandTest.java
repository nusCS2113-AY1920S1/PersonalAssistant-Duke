import command.Command;
import command.RemindCommand;
import exception.DukeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.ParserFactory;
import storage.Storage;
import task.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RemindCommandTest {

    private static ArrayList<Task> list;
    private static TaskList tasks;
    private static File file;
    private static Storage storage;
    private static Command reminder;

    /**
     * Setups the necessary base to carry out the test operations.
     */
    @BeforeAll
    public static void setup() {
        list = new ArrayList<>();
        tasks = new TaskList(list);
        file = new File(System.getProperty("user.dir") + "/src/test/ArrayList");
        storage = new Storage(file);
        reminder = new RemindCommand(1, 5);

        Todo filler = new Todo("1");
        tasks.add(filler);
    }

    @Test
    public void testReminder() throws DukeException {
        Task testTask = new Deadline("test", LocalDateTime.of(2019, 8, 1, 12, 0));
        tasks.add(testTask);
        reminder.execute(tasks, storage);
        Assertions.assertNotNull(testTask.reminder);
        Assertions.assertTrue(testTask.checkReminderTrigger());
    }

    @Test
    public void testReminderNotTriggered() throws DukeException {
        Task testTask = new Event("test",
                LocalDateTime.of(3019, 8, 1, 12, 0),
                LocalDateTime.of(3019, 8, 2, 12, 0));
        list.add(testTask);
        TaskList tasks = new TaskList(list);
        reminder.execute(tasks, storage);
        Assertions.assertNotNull(testTask.reminder);
        Assertions.assertFalse(testTask.checkReminderTrigger());
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse("remind");
        });
        Assertions.assertThrows(DukeException.class, () -> {
            ParserFactory.parse(("remind 0 in 3 days"));
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Command test = new RemindCommand(100, 3);
            test.execute(tasks, storage);
        });
    }

    @AfterAll
    public static void teardownSetup() {
        assert file.delete();
    }
}
