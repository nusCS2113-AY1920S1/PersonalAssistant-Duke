package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;

public class DeleteTaskCommandTest {
    @Test
    public void testDeleteCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddEventCommand eventCommand = new AddEventCommand(false,"event Birthday Party /at 12/12/2019 1830");
        eventCommand.execute(newTaskList, newUi, newStorage);

        DeleteTaskCommand newDeleteTaskCommand = new DeleteTaskCommand(false, "delete 1");
        newDeleteTaskCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(0, newTaskList.getSize());
        newStorage.saveToFile();
        assertTrue(tempFile.exists());
        assertTrue(Files.readAllLines(Paths.get(tempFile.getPath())).isEmpty());
    }
}