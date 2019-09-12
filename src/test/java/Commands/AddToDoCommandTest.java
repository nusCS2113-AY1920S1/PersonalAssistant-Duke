import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToDoCommandTest {
    @Test
    public void TestToDoCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke", ".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddToDoCommand todoCommand = new AddToDoCommand(false, "todo hello world");
        todoCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(newTaskList.getSize(), 1);
        assertTrue(tempFile.exists());
        assertEquals(Files.readAllLines(Paths.get(tempFile.getPath())).get(0), "T | 0 | hello world");
    }
}