package models.task;

import org.junit.jupiter.api.Test;

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
        TaskList taskList = new TaskList();
        ArrayList<String> taskRequirements = new ArrayList<>();

        Task task1 = new Task("task1", 1, null,100, TaskState.OPEN,taskRequirements);
        Task task2 = new Task("task2", 2, null,100, TaskState.OPEN,taskRequirements);
        Task task3 = new Task("task3", 3, null,100, TaskState.OPEN,taskRequirements);
        Task task4 = new Task("task4", 4, null,100, TaskState.OPEN,taskRequirements);
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.addTask(task3);
        taskList.addTask(task4);
        assertNotNull(taskList.getTaskList());

        String testTaskListString = "[1. task4 | Priority: 4 | Due: -- | Credit: 100 | State: OPEN, "
                + "2. task3 | Priority: 3 | Due: -- | Credit: 100 | State: OPEN, "
                + "3. task2 | Priority: 2 | Due: -- | Credit: 100 | State: OPEN, "
                + "4. task1 | Priority: 1 | Due: -- | Credit: 100 | State: OPEN]";
        assertEquals(taskList.getAllTaskDetails().toString(), testTaskListString);

        String testRemoveTaskListString = "[1. task4 | Priority: 4 | Due: -- | Credit: 100 | State: OPEN, "
                + "2. task2 | Priority: 2 | Due: -- | Credit: 100 | State: OPEN, "
                + "3. task1 | Priority: 1 | Due: -- | Credit: 100 | State: OPEN]";
        taskList.removeTask(2);
        assertEquals(taskList.getAllTaskDetails().toString(), testRemoveTaskListString);
    }
}