import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddDeadlineCommandTest {
    @Test
    public void TestToDoCommand() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke",".txt");
        tempFile.deleteOnExit();

        TaskList newTaskList = new TaskList();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        AddDeadLineCommand todoCommand = new AddDeadLineCommand(false,"deadline To complete work /by 1/1/2019 1830");
        todoCommand.execute(newTaskList, newUi, newStorage);

        assertEquals(newTaskList.getSize(), 1);
        assertTrue(tempFile.exists());
        assertEquals(Files.readAllLines(Paths.get(tempFile.getPath())).get(0) , "D | 0 | To complete work | 1/1/2019 1830");
    }
}