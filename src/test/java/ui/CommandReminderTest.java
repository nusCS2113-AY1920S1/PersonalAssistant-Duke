package ui;

import duke.exception.DukeException;
import executor.command.CommandReminder;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandReminderTest {

    @Test
    void execute() {
        String date = LocalDate.now().toString();
        Task testTask = null;
        try {
            testTask = TaskList.createTask(TaskType.DEADLINE, "deadline finish assignment /by "
                    + date);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        StorageManager storageManager = new StorageManager();
        storageManager.getTaskList().addTask(testTask);
        CommandReminder testCommand = new CommandReminder("deadline finish assignment /by "
                + date);
        testCommand.execute(storageManager);
        Task mainTask = storageManager.getTaskList().get(0);
        assertEquals(TaskType.DEADLINE, mainTask.getTaskType());
        assertEquals("finish assignment", mainTask.getTaskName());
        assertEquals("by", mainTask.getDetailDesc());
        assertEquals(LocalDate.now(),mainTask.getDate());
    }

}
