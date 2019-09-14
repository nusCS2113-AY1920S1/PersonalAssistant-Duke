import Duke.DukeException;
import Duke.Parser;
import Duke.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    public void test(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runEvent(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[E][✗] test (at: 0000)");
    }

    @Test
    public void birthdayAt_myBday(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runEvent(taskList.getData(), input, 2);
        assertEquals(taskList.get(0).getFullString(), "[E][✓] bday (at: 06/06/2019)");
    }
}
