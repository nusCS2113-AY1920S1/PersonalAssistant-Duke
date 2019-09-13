package Duke.Task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {
    private TaskList tasks = new TaskList();

    @Test
    void testDeleteTask() {
        tasks.add(new Event("test", "roger"));
        tasks.add(new Todo("test2"));

        Task deletedTask = tasks.deleteTask(1);
        assertEquals(deletedTask.toString(), "[E][\u2718] test (at: roger)\n");
    }

    @Test
    void testIsCompletedTask() {
        tasks.add(new Todo("test"));

        assertFalse(tasks.isCompletedTask(1));
    }

    @Test
    void testDoneTask() {
        tasks.add(new Todo("test"));
        Task doneTask = tasks.doneTask(1);

        assertEquals(doneTask.toString(), "[T][\u2713] test\n");
    }

    @Test
    void testFindTask() {
        tasks.add(new Todo("test"));
        tasks.add(new Todo("test2"));
        tasks.add(new Todo("test3"));

        TaskList foundTasks = tasks.findTask("test");
        assertEquals(foundTasks, tasks);
    }
}