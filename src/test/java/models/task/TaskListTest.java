package models.task;

import org.junit.jupiter.api.Test;
import views.CLIView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskListTest {

    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testAddTask() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        Task task = new Task("task", 5, null,100, TaskState.OPEN,taskRequirements);
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        assertEquals("task | Priority: 5 | Due: -- | Credit: 100 | State: OPEN",taskList.getTask(1).getDetails());
    }

    @Test
    public void testRemoveTask() {
        ArrayList<String> taskRequirements = new ArrayList<>();
        Task task = new Task("task", 5, null,100, TaskState.OPEN,taskRequirements);
        TaskList taskList = new TaskList();
        taskList.addTask(task);
        taskList.removeTask(1);
        assertEquals(0,taskList.getSize());
    }

    @Test
    public void testGetTaskList() {
       
    }
}