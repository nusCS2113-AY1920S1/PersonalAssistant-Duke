import command.Command;
import command.RemindCommand;
import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import storage.Storage;
import task.Deadline;
import task.Task;
import task.TaskList;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RemindCommandTest {

    ArrayList<Task> list = new ArrayList<>();
    File file = new File(System.getProperty("user.dir") + "/src/test/ArrayList");
    Storage storage = new Storage(file);

    Command reminder = new RemindCommand(0, 5);

    @Test
    public void testReminder() throws DukeException {
        Task testTask = new Deadline("test", LocalDateTime.of(2019, 8, 1, 12, 0));
        list.add(testTask);
        TaskList tasks = new TaskList(list);
        reminder.execute(tasks, storage);
        Assertions.assertEquals(testTask.remindInHowManyDays, 5);
        Assertions.assertTrue(testTask.checkReminderTrigger());
    }

    @Test
    public void whenExceptionThrown() {
        Assertions.assertThrows(DukeException.class, () -> {
            Parser.parse("remind");
        });
    }
}
