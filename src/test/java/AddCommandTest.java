import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import task.Deadline;
import task.Task;
import task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class implements the unit testing code for the AddCommand Test class and
 * particularly tests IsClash().
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class AddCommandTest {
    private LocalDateTime testDate = LocalDateTime.of(2, 2, 2, 2, 2, 2, 2);

    @Test
    public void testisClash() {
        Deadline task1 = new Deadline("tester", testDate);
        Deadline task2 = new Deadline("tester", testDate);
        ArrayList<Task> test = new ArrayList<>();
        test.add(task1);
        TaskList testisClash = new TaskList(test);
        Assertions.assertEquals(testisClash.isClash(task2), true);
    }
}
