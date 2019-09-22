package wallet.model.task;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    TaskList taskList = new TaskList(new ArrayList<Task>());

    @Test
    public void createTaskTest() {
        String type = "todo";
        String desc = "read book";

        Task task = taskList.createTask(type, desc);
        assertEquals("[T][✘] read book", task.toString());

        type = "event";
        desc = "exam /at 10/10/2019 1000";

        task = taskList.createTask(type, desc);
        assertEquals("[E][✘] exam (at: 10 Oct 2019 10:00AM)", task.toString());

        type = "deadline";
        desc = "assignment /by 1/1/2019 2359";

        task = taskList.createTask(type, desc);
        assertEquals("[D][✘] assignment (by: 01 Jan 2019 11:59PM)", task.toString());
    }

    @Test
    public void addTaskTest() {
        Task task = new Todo("homework");
        taskList.addTask(task);
        for (Task t : taskList.getTaskList()) {
            assertEquals("[T][✘] homework", t.toString());
        }
    }

    @Test
    public void getTaskListSizeTest() {
        int size = taskList.getTaskListSize();
        assertEquals(0, size);
    }

    @Test
    public void getTaskTest() {
        Task t = new Todo("test getTask");
        taskList.addTask(t);
        Task newTask = taskList.getTask(0);
        assertEquals("[T][✘] test getTask", newTask.toString());
    }

    @Test
    public void modifyTaskTest() {
        Task t = new Todo("test modifyTask");
        taskList.addTask(t);
        Task newTask = taskList.createTask("event", "modified Task /at 1/1/2020 0000");
        taskList.modifyTask(0, newTask);
        assertEquals("[E][✘] modified Task (at: 01 Jan 2020 12:00AM)", taskList.getTask(0).toString());
    }
}
