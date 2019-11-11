package ui;

import duke.exception.DukeException;
import executor.command.CommandReminder;
import storage.task.Task;
import storage.task.TaskList;
import storage.task.TaskType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandReminderTest {

    @Test
    void execute() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        Task testTask = null;
        try {
            testTask = TaskList.createTask(TaskType.DEADLINE, "deadline finish assignment /by "
                    + date.format(dateFormatter) + " 2359");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        StorageManager storageManager = new StorageManager();
        storageManager.getTaskList().addTask(testTask);
        CommandReminder testCommand = new CommandReminder("deadline finish assignment /by "
                + date.format(dateFormatter) + " 1900");
        testCommand.execute(storageManager);
        Task mainTask = storageManager.getTaskList().get(0);
        assertEquals(TaskType.DEADLINE, mainTask.getTaskType());
        assertEquals("finish assignment", mainTask.getTaskName());
        assertEquals("by", mainTask.getDetailDesc());
        assertEquals(LocalDate.now(),mainTask.getDate());
    }

}
