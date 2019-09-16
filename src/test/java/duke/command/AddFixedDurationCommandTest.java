package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import duke.task.Storage;
import duke.task.Ui;
import duke.task.TaskList;
import duke.task.DukeException;
import duke.command.AddFixedDurationCommand;
import duke.task.FixedDuration;

public class AddFixedDurationCommandTest {
    @Test
    public void TestDeadlineCommand() throws DukeException, IOException {
        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage("/data/tasks.txt");

        AddFixedDurationCommand fixedDurationCommand = new AddFixedDurationCommand(false,"fixed read sales report /for 2 DAYS 5 HRS 57 MINS 20 SEC");
        fixedDurationCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertEquals( "[F][NOT DONE] read sales report (for: 2 Days 5:57:20)", newTaskList.getTask(0).toString());
        newTaskList.getTask(0).markAsDone();
        assertEquals( "[F][DONE] read sales report (for: 2 Days 5:57:20)", newTaskList.getTask(0).toString());
    }
}