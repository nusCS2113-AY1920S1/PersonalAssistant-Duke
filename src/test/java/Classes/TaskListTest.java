import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void TestAddToTaskList() {
        TaskList newTaskList = new TaskList();
        newTaskList.addToArrayList(new ToDo("Hello World"));

        assertEquals(newTaskList.getSize(), 1);
        assertEquals(newTaskList.getTask(0).toString(), "[T][NOT DONE] Hello World");

        newTaskList.deleteFromArrayList(0);
        assertEquals(newTaskList.getSize(), 0);
    }
}