import duchess.logic.commands.exceptions.DukeException;
import duchess.storage.task.Task;
import duchess.storage.task.TaskList;
import duchess.storage.task.Todo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {
    @Test
    void add_works_correctly() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Todo(List.of("read book".split(" ")));
        taskList.add(task);
        assertEquals(task, taskList.get(0));
    }

    @Test
    void get_throws_outOfBounds_exception() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Todo(List.of("read book".split(" ")));
        taskList.add(task);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.get(1);
        });
    }

    @Test
    void delete_works_correctly() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Todo(List.of("read book".split(" ")));
        taskList.add(task);
        taskList.delete(0);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.get(0);
        });
    }

    @Test
    void getTasks_works_correctly() throws DukeException {
        TaskList taskList = new TaskList();
        Task task = new Todo(List.of("read book".split(" ")));
        taskList.add(task);
        assertTrue(taskList.getTasks().size() == 1);
        assertEquals(taskList.getTasks().get(0), task);
    }
}
