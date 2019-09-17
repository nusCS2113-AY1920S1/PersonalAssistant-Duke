import Task.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to see if TaskList can be created
 */

public class TaskListTest {
    protected TaskList taskList = new TaskList();

    @Test
    public void alwaysTrue () {
        assertEquals(2,2);
    }
    @Test
    public void dateTest () {
        String date = "2/12/2019 1800";
        String output = taskList.dateConvert(date);
        assertEquals("2nd of December 2019, 6pm", output);
    }

    @Test
    public void OrdinalTest () {
        int num = 19;
        String output = taskList.numOrdinal(num);
        assertEquals("19th", output);
    }
}
