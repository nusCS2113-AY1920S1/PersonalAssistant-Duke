package duke.command;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import duke.task.*;

public class AddDeadlineCommandTest {
    @Test
    public void TestDeadlineCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddDeadLineCommand deadLineCommand = new AddDeadLineCommand(false,"deadline To complete work /by 1/1/2019 1830");
        deadLineCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(1, newTaskList.getSize());
        assertTrue(tempFile.exists());
        assertEquals( "D | 0 | To complete work | 1/1/2019 1830", Files.readAllLines(Paths.get(tempFile.getPath())).get(0));
    }
}