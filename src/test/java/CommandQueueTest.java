import executor.command.CommandQueue;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandQueueTest {

    @Test
    void execute() {
        TaskList taskList = new TaskList();
        Task testTask = TaskList.createTask(TaskType.EVENT,"something/by somewhen");
        taskList.addTask(testTask);
        CommandQueue testCommand = new CommandQueue("Queue 1 EventIce Cream Party / Tomorrow");
        testCommand.execute(taskList);

        Task mainTask = taskList.getList().get(0);
        assertEquals(true, mainTask.isQueuedTasks());
        Task queuedTask = mainTask.getQueuedTasks().getList().get(0);
        assertEquals(TaskType.EVENT, queuedTask.getTaskType());
        assertEquals("Ice Cream Party", queuedTask.getTaskName());
        assertEquals("", queuedTask.getDetailDesc());
        assertEquals("Tomorrow", queuedTask.getTaskDetails());
    }
}