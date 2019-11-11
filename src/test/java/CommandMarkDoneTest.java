import duke.exception.DukeException;
import executor.command.CommandMarkDone;
import storage.task.Task;
import storage.task.TaskList;
import storage.task.TaskType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandMarkDoneTest {

    @Test
    void loadQueuedTasks() {
        Task mainTask = null;
        Task queuedTask = null;
        try {
            mainTask = TaskList.createTask(TaskType.EVENT, "something/by somewhen");
            queuedTask = TaskList.createTask(TaskType.DEADLINE, "Something Else / rly");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        assert mainTask != null;
        assert queuedTask != null;
        TaskList queuedTaskList = new TaskList();
        queuedTaskList.addTask(queuedTask);
        mainTask.setQueuedTasks(queuedTaskList);
        StorageManager storageManager = new StorageManager();
        storageManager.getTaskList().addTask(mainTask);
        CommandMarkDone c = new CommandMarkDone("Done1");
        c.execute(storageManager);

        Task loadedTask = storageManager.getTaskList().get(1);
        assertEquals(true, mainTask.getIsDone());
        assertEquals(false, mainTask.isQueuedTasks());
        assertEquals(TaskType.DEADLINE, loadedTask.getTaskType());
        assertEquals("Something Else", loadedTask.getTaskName());
        assertEquals("", loadedTask.getDetailDesc());
        assertEquals("rly", loadedTask.getTaskDetails());
    }
}