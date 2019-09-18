import duke.command.CommandQueue;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
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
        System.out.println("DONE!");

        Task mainTask = taskList.getList().get(0);
        assertEquals(true, mainTask.isQueuedTasks());
        Task queuedTask = mainTask.getQueuedTasks().getList().get(0);
        assertEquals(TaskType.EVENT, queuedTask.taskType);
        assertEquals("Ice Cream Party", queuedTask.taskName);
        assertEquals("", queuedTask.detailDesc);
        assertEquals("Tomorrow", queuedTask.taskDetails);
    }
}