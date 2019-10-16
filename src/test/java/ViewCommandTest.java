import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import task.Deadline;
import task.Task;
import task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class implements the unit testing code for the ViewCommand.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ViewCommandTest {
    private LocalDateTime testDate = LocalDateTime.of(2019, 9, 23, 2, 2, 2, 2);

    @Test
    public void testView() {
        Deadline task1 = new Deadline("tester", testDate);
        ArrayList<Task> test = new ArrayList<>();
        ArrayList<Task> testIfNull = new ArrayList<>();
        test.add(task1);
        TaskList testisClash = new TaskList(test);

        testIfNull = testisClash.schedule("23/09/2019");
        Assertions.assertTrue((testIfNull.size() > 0));
    }
}
