import CustomExceptions.RoomShareException;
import Enums.Priority;
import Model_Classes.Task;
import Operations.TaskList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private static String description = "task1";
    private static Task ts = new Task(description);
    private static Task ts2 = new Task("task2");
    private static TaskList tl = new TaskList(new ArrayList<Task>());

    @Test
    void add() {
        tl.add(ts);
        assertEquals("[\u2718] task1 (null)", ts.toString());
    }

    @Test
    void find() {
        tl.find("task");
        assertEquals("[\u2718] task1 (null)", ts.toString());
    }

    @Test
    void reorder() {
        tl.reorder(1, 2);
        assertEquals("[\u2718] task2 (null)\n" +
                "[\u2718] task1 (null)", ts2.toString() + "\n" + ts.toString());
    }

}