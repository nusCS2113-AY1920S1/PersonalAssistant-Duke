package duke.task;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void testFileLoad() throws DukeException, IOException {
        File tempFile = File.createTempFile("duke", ".txt");
        tempFile.deleteOnExit();

        FileWriter writer = new FileWriter(tempFile);
        writer.write("T | 0 | Hello World\n");
        writer.write("D | 0 | To Do | 02/2/2019 1830");
        writer.flush();
        writer.close();

        Storage newStorage = new Storage(tempFile.getPath());
        ArrayList<Task> newArrayList = newStorage.load();
        TaskList newTaskList = new TaskList(newArrayList);
        assertEquals(2, newTaskList.getSize());
        assertEquals("[D][NOT DONE] To Do (by: 2/2/2019 1830)", newTaskList.getTask(1).toString());
    }
}