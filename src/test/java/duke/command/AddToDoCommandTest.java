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

public class AddToDoCommandTest {
    @Test
    public void testAddCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke", ".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddToDoCommand todoCommand = new AddToDoCommand(false, "todo hello world");
        todoCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertTrue(tempFile.exists());
        assertEquals("T | 0 | hello world", Files.readAllLines(Paths.get(tempFile.getPath())).get(0));
    }
}