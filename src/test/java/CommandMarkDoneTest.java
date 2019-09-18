import duke.command.CommandMarkDone;
import duke.command.CommandQueue;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandMarkDoneTest {

    @Test
    void loadQueuedTasks() {
        TaskList taskList = new TaskList();
        TaskList queuedTaskList = new TaskList();
        Task mainTask = TaskList.createTask(TaskType.EVENT,"something/by somewhen");
        Task queuedTask = TaskList.createTask(TaskType.DEADLINE, "Something Else / rly");
        queuedTaskList.addTask(queuedTask);
        mainTask.setQueuedTasks(queuedTaskList);
        taskList.addTask(mainTask);
        CommandMarkDone c = new CommandMarkDone("Done1");
        c.execute(taskList);

        Task loadedTask = taskList.getList().get(1);
        assertEquals(true, mainTask.isDone);
        assertEquals(false, mainTask.isQueuedTasks());
        assertEquals(TaskType.DEADLINE, loadedTask.taskType);
        assertEquals("Something Else", loadedTask.taskName);
        assertEquals("", loadedTask.detailDesc);
        assertEquals("rly", loadedTask.taskDetails);
    }
}