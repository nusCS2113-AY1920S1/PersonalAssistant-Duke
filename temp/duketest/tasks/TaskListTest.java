package duketest.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.tasks.Todo;

import java.util.List;
import java.util.ArrayList;

public class TaskListTest {

    @Test
    public void checkAddTask() {
        TaskList obj = new TaskList();
        Task store =  new Todo("Borrow book");
        obj.addTask(store);
        assertEquals(store,obj.getTask(0));
    }

    @Test
    public void checkDeleteTask() {
        TaskList obj = new TaskList();
        Task store = new Todo("join cca");
        obj.addTask(store);
        obj.deleteTask(0);
        assertThrows(IndexOutOfBoundsException.class,() -> obj.getTask(1));
    }

    @Test
    public void checkNumTasks() {
        TaskList obj = new TaskList();
        obj.addTask(new Todo("quit cca"));
        assertEquals(1,obj.numTasks());
    }

    @Test
    public void checkGetTask() {
        TaskList obj = new TaskList();
        obj.addTask(new Todo("Borrow book"));
        Task num2 = new Todo("Return book");
        obj.addTask(num2);
        assertEquals(num2.toString(),obj.getTask(1).toString());
    }

    @Test
    public void checkGetAllTasks() {
        TaskList obj = new TaskList();
        obj.addTask(new Todo("return book"));
        obj.addTask(new Todo("study for CS2113"));
        List<Task> storeTest = new ArrayList<>();
        storeTest = obj.getAllTasks();
        assertTrue(storeTest.size() == 2);
        Task taskA = new Todo("return book");
        Task taskB = new Todo("study for CS2113");
        assertEquals(storeTest.get(0).toString(),taskA.toString());
        assertEquals(storeTest.get(1).toString(),taskB.toString());
    }
}
