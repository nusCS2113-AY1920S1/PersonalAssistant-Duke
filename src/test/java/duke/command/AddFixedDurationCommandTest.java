package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import duke.task.Storage;
import duke.task.Ui;
import duke.task.TaskList;
import duke.task.DukeException;

public class AddFixedDurationCommandTest {
    @Test
    public void testDeadlineCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("tasks",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddFixedDurationCommand fixedDurationCommand = new AddFixedDurationCommand(false,
                "fixed read sales report /for 2 DAYS 5 HRS 57 MINS 20 SEC");
        fixedDurationCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertEquals("[F][NOT DONE] read sales report (for: 2 Days 5:57:20)", newTaskList.getTask(0).toString());
        newTaskList.getTask(0).markAsDone();
        assertEquals("[F][DONE] read sales report (for: 2 Days 5:57:20)", newTaskList.getTask(0).toString());

        fixedDurationCommand = new AddFixedDurationCommand(false,
                "fixed read books /for 1 DAY 3 HRS 1 MIN");
        fixedDurationCommand.execute(newTaskList, newUi, newStorage);

        assertEquals("[F][NOT DONE] read books (for: 1 Day 3:01:00)", newTaskList.getTask(1).toString());
        newTaskList.getTask(1).markAsDone();
        assertEquals("[F][DONE] read books (for: 1 Day 3:01:00)", newTaskList.getTask(1).toString());
    }
}