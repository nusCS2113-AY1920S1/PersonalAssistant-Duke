import chronologer.task.Deadline;
import chronologer.task.Task;
import chronologer.task.TaskList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tests both positive/negative cases for the remind list that the GUI obtains.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ReminderListTest {

    private static ArrayList<Task> testCoreList;
    private static TaskList testCore;
    private static LocalDateTime byDate;
    private static Deadline testDeadline;

    @BeforeAll
    public static void setup() {
        testCoreList = new ArrayList<Task>();
        testCore = new TaskList(testCoreList);
    }

    @Test
    public void testNegativeReminder() {
        byDate = LocalDateTime.now().plusDays(4);
        testDeadline = new Deadline("Test", byDate);
        testCore.add(testDeadline);
        ArrayList<String> reminderList = testCore.fetchReminders();
        Assertions.assertEquals(0, reminderList.size());
    }

    @Test
    public void testReminder() {
        byDate = LocalDateTime.now().plusHours(23);
        testDeadline = new Deadline("Test", byDate);
        testCore.add(testDeadline);
        ArrayList<String> reminderList = testCore.fetchReminders();
        Assertions.assertEquals(1, reminderList.size());
    }
}
