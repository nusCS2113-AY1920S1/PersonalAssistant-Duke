import chronologer.task.Deadline;
import chronologer.task.Priority;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tests both positive/negative cases for priority list that the GUI obtains.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class PriorityListTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static LocalDateTime byDate;
    private static Deadline testDeadline;

    @BeforeAll
    static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
        byDate = LocalDateTime.of(2001, 8, 1, 1, 0);
        testDeadline = new Deadline("Test", byDate);
        testCore.add(testDeadline);
    }

    @Test
    public void testPriorityList() {
        testCore.getTasks().get(0).setPriority(Priority.MEDIUM);
        ArrayList<String> priorityList = testCore.obtainPriorityList("01/08/2001");
        Assertions.assertEquals(1, priorityList.size());
    }

    @Test
    public void testNegativePriorityList() {
        testCore.getTasks().get(0).setPriority(Priority.LOW);
        ArrayList<String> priorityList = testCore.obtainPriorityList("01/08/2001");
        Assertions.assertEquals(0, priorityList.size());
    }
}
