//@@author kkeejjuunn

package duke.models.tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import duke.exceptions.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskManagerTest {

    @Test
    public void getTaskByDescriptionTest() throws DukeException {
        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        testTaskList.add(task4);
        TaskManager taskManager = new TaskManager(testTaskList);

        ArrayList<Task> actualTaskList = new ArrayList<>();

        actualTaskList.add(task2);
        assertEquals(taskManager.getTaskByDescription("injection"),actualTaskList);
        actualTaskList = new ArrayList<>();
        actualTaskList.add(task1);
        actualTaskList.add(task4);
        assertEquals(taskManager.getTaskByDescription("Take medicine"),actualTaskList);
        actualTaskList = new ArrayList<>();
        assertEquals(taskManager.getTaskByDescription("Help with operation"),actualTaskList);
    }

    @Test
    public void addTaskTest() throws DukeException {
        ArrayList<Task> testTaskList = new ArrayList<>();
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task(1,"Do mammogram");
        taskManager.addTask(task1);
        Task task2 = new Task(2,"Do blood test");
        taskManager.addTask(task2);
        assertEquals(taskManager.getTaskList().size(),2);
        assertEquals(taskManager.getTask(1),task1);
        assertEquals(taskManager.getTask(2),task2);
    }

    @Test
    public void deleteTaskTest() throws DukeException {
        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        testTaskList.add(task4);
        TaskManager taskManager = new TaskManager(testTaskList);

        taskManager.deleteTask(1);
        assertEquals(taskManager.getTaskList().size(), 3);
        taskManager.deleteTask(2);
        assertEquals(taskManager.getTaskList().size(),2);

        try {
            taskManager.deleteTask(5);
        } catch (Exception e) {
            String expectedMessage = "Oops! The task with id 5 does not exist.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void doesExistTest() throws DukeException {
        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        TaskManager taskManager = new TaskManager(testTaskList);

        assertTrue(taskManager.doesExist(1));
        assertTrue(taskManager.doesExist(2));
        assertFalse(taskManager.doesExist(3));
        assertFalse(taskManager.doesExist(4));
    }

    @Test
    public void getTaskTest() throws DukeException {
        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        TaskManager taskManager = new TaskManager(testTaskList);

        assertEquals(taskManager.getTask(1),task1);
        assertEquals(taskManager.getTask(2),task2);

        try {
            taskManager.getTask(3);
        } catch (Exception e) {
            String expectedMessage = "Oops! The task with id 3 does not exist.";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void getTaskListTest() {
        ArrayList<Task> testTaskList = new ArrayList<>();
        Task task1 = new Task(1,"Take medicine Panadol");
        testTaskList.add(task1);
        Task task2 = new Task(2,"Do injection");
        testTaskList.add(task2);
        Task task3 = new Task(3,"Meeting with doctor");
        testTaskList.add(task3);
        Task task4 = new Task(4,"Take medicine Decolgen");
        TaskManager taskManager = new TaskManager(testTaskList);

        assertEquals(taskManager.getTaskList(),testTaskList);
    }
}
