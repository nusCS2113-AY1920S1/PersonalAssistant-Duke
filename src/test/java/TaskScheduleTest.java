import command.Command;
import command.TaskScheduleCommand;
import exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.Deadline;
import task.Task;
import task.TaskList;
import task.Todo;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskScheduleTest {

    private static ArrayList<Task> list;
    private static TaskList tasks;
    private static File file;
    private static Storage storage;

    private Field[] getTaskScheduleCommandFields(Command command) throws NoSuchFieldException {
        Field[] commandFields = new Field[3];
        commandFields[0] = command.getClass().getDeclaredField("durationToSchedule");
        commandFields[1] = command.getClass().getDeclaredField("indexOfTask");
        commandFields[2] = command.getClass().getDeclaredField("indexOfDeadline");
        commandFields[0].setAccessible(true);
        commandFields[1].setAccessible(true);
        commandFields[2].setAccessible(true);
        return commandFields;
    }

    /**
     * Setups the necessary base to carry out the test operations.
     */
    @BeforeAll
    public static void setup() {
        list = new ArrayList<>();
        tasks = new TaskList(list);
        file = new File(System.getProperty("user.dir") + "/src/test/ArrayList");
        storage = new Storage(file);

        LocalDateTime fromDate = LocalDateTime.of(2001, 1, 1, 1, 0);
        Deadline deadline = new Deadline("0", fromDate);
        tasks.add(deadline);
        Todo filler = new Todo("1", 2);
        tasks.add(filler);
    }

    @Test
    public void testCommand() throws DukeException, NoSuchFieldException, IllegalAccessException {
        Field[] commandFields;

        Todo expectedTodo = (Todo) tasks.getTasks().get(1);
        Deadline expectedDeadline = (Deadline) tasks.getTasks().get(0);

        Command test = new TaskScheduleCommand(1, 0);
        test.execute(tasks, storage);
        commandFields = getTaskScheduleCommandFields(test);

        Long testDuration = (Long) commandFields[0].get(test);
        int testIndexOfTodo = (int) commandFields[1].get(test);
        Todo testTodo = (Todo) tasks.getTasks().get(testIndexOfTodo);
        int testIndexOfDeadline = (int) commandFields[2].get(test);
        Deadline testDeadline = (Deadline) tasks.getTasks().get(testIndexOfDeadline);

        Assertions.assertEquals(testDuration, 2);
        Assertions.assertSame(testTodo, expectedTodo);
        Assertions.assertSame(testDeadline, expectedDeadline);
    }

    @Test
    public void testException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            Command test = new TaskScheduleCommand(-1, 0);
            test.execute(tasks, storage);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            Command test = new TaskScheduleCommand(1, -1);
            test.execute(tasks, storage);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            Command test = new TaskScheduleCommand(100, 0);
            test.execute(tasks, storage);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            Command test = new TaskScheduleCommand(1, 100);
            test.execute(tasks, storage);
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Command test = new TaskScheduleCommand(1, 1);
            test.execute(tasks, storage);
        });
        Assertions.assertThrows(DukeException.class, () -> {
            Command test = new TaskScheduleCommand(0, 0);
            test.execute(tasks, storage);
        });
    }
}
