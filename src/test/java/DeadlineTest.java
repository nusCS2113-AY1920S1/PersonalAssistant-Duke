import Duke.DukeException;
import Duke.Parser;
import Duke.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {
    @Test
    public void test(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runDeadline(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[D][✗] test (by: 0000)");
    }

    @Test
    public void examBy_Date(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runDeadline(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[D][✗] exam (by: 01/01/2019)");
    }

}
