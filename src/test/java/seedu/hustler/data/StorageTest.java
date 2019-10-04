package seedu.hustler.data;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import seedu.hustler.task.TaskList;
import seedu.hustler.task.Task;

/**
 * Test class for storage.
 */
public class StorageTest {

    @Test

    /**
     * Dummy test for storage.
     */
    public void dummyTest() {

        File history = new File("data/test.txt");
        history.delete();

        Storage storage = new Storage("data/test.txt");

        TaskList list = new TaskList(storage.load());
        assertArrayEquals(list.return_list().toArray(),
            (new ArrayList<Task>()).toArray());
        list.add("todo", "Work on CS2113T feature list");
        list.add("deadline", "Finish Feature list /by tommorrow");
        list.add("event", "Student life fair /at 1/1/2019 2400");

        try {
            storage.save(list.return_list());
        } catch (IOException e) {
            fail("Couldn't be saved.");
        }

        history.delete();
    }
}
