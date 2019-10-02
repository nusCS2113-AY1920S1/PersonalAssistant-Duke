package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import duke.task.DukeException;
import duke.task.TaskList;
import duke.task.Storage;
import duke.task.Ui;

public class AddDoWithinPeriodCommandTest {
    @Test
    public void testDeadlineCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddDoWithinPeriodCommand doWithinPeriodCommand = new AddDoWithinPeriodCommand(false,
                "dowithin To complete work /from 1/1/2019 1830 /to 3/2/2019 1900");
        doWithinPeriodCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertEquals("[W][NOT DONE] To complete work (within: 1/1/2019 1830 - 3/2/2019 1900)",
                newTaskList.getTask(0).toString());

        newTaskList.getTask(0).markAsDone();
        assertEquals("[W][DONE] To complete work (within: 1/1/2019 1830 - 3/2/2019 1900)",
                newTaskList.getTask(0).toString());

        try {
            doWithinPeriodCommand = new AddDoWithinPeriodCommand(false,
                    "dowithin To complete work /from 5/12/2019 1830 /to 3/2/2019 1900");
            doWithinPeriodCommand.execute(newTaskList, newUi, newStorage);
        } catch (DukeException e) {
            assertEquals("duke.task.DukeException: Start time cannot be later than end time.", e.toString());
        }



    }
}