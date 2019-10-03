import duke.command.CommandReminder;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.TaskType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommandReminderTest {

    @Test
    void execute() {
        TaskList taskList = new TaskList();
        Task testTask = TaskList.createTask(TaskType.EVENT,"something/by somewhen");
        taskList.addTask(testTask);
        CommandReminder testCommand = new CommandReminder();
        testCommand.execute(taskList);

        Task mainTask = taskList.getList().get(0);
        assertEquals(true, mainTask.isQueuedTasks());
//        Task queuedTask = mainTask.getQueuedTasks().getList().get(0);
//        assertEquals(TaskType.EVENT, queuedTask.getTaskType());
//        assertEquals("Ice Cream Party", queuedTask.getTaskName());
//        assertEquals("", queuedTask.getDetailDesc());
//        assertEquals("Tomorrow", queuedTask.getTaskDetails());
    }
}
