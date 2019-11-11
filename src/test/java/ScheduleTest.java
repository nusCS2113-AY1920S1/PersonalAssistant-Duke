import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tests the schedule obtained for a given day is accurate.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ScheduleTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static LocalDateTime byDate;
    private static Deadline testDeadline;

    @BeforeAll
    static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
        byDate = LocalDateTime.of(2001, 3, 1, 1, 0);
        testDeadline = new Deadline("Test", byDate);
        testCore.add(testDeadline);
    }

    @Test
    public void testSchedule() {
        ArrayList<String> scheduleReceived = testCore.scheduleForDay("01/03/2001");
        Assertions.assertEquals(1, scheduleReceived.size());
    }

    @Test
    public void testNegativeSchedule() {
        ArrayList<String> scheduleReceived = testCore.scheduleForDay("01/02/2001");
        Assertions.assertEquals(0, scheduleReceived.size());
    }

}
