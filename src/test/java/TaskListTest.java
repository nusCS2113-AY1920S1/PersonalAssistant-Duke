import duke.task.TaskList;
import org.junit.jupiter.api.Test;

import java.util.Date;

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
        String input = "02/12/2019 1800";
        Date date = taskList.dateConvert(input);
        String newDate = taskList.dateToStringFormat(date);
        assertEquals("2nd of December 2019, 6pm", newDate);
    }

    @Test
    public void OrdinalTest () {
        int num = 19;
        String output = taskList.numOrdinal(num);
        assertEquals("19th", output);
    }
}
