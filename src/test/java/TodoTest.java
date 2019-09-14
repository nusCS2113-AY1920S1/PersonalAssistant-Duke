import Duke.DukeException;
import Duke.Parser;
import Duke.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void test(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runTodo(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[T][✗] test");
    }

    @Test
    public void jog(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runTodo(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[T][✗] jog");
    }

    @Test
    public void todo(String input) throws DukeException {
        TaskList taskList = new TaskList();
        Parser.runTodo(taskList.getData(), input, 1);
        assertEquals(taskList.get(0).getFullString(), "[T][✗] todo");
    }

}
