import duke.exception.DukeException;
import executor.command.CommandQueue;
import storage.task.Task;
import storage.task.TaskList;
import storage.task.TaskType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandQueueTest {

    @Test
    void execute() {
        Task testTask = null;
        try {
            testTask = TaskList.createTask(TaskType.EVENT, "something/by somewhen");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        assert testTask != null;
        StorageManager storageManager = new StorageManager();
        storageManager.getTaskList().addTask(testTask);
        CommandQueue testCommand = new CommandQueue("Queue 1 /task EventIce Cream Party / Tomorrow");
        testCommand.execute(storageManager);

        Task mainTask = storageManager.getTaskList().get(0);
        assertEquals(true, mainTask.isQueuedTasks());
        Task queuedTask = mainTask.getQueuedTasks().get(0);
        assertEquals(TaskType.EVENT, queuedTask.getTaskType());
        assertEquals("Ice Cream Party", queuedTask.getTaskName());
        assertEquals("", queuedTask.getDetailDesc());
        assertEquals("Tomorrow", queuedTask.getTaskDetails());
    }
}