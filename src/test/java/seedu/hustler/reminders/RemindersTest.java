package seedu.hustler.reminders;

import org.junit.jupiter.api.Test;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.Reminders;
import seedu.hustler.task.Task;
import seedu.hustler.task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for deadline.
 */

public class RemindersTest {

    @Test

    /**
     * Check if oneDay reminder exist.
     */
    public void dummyTest() {
        ArrayList<Task> temp = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2019,8,1,1,1,1);
        temp.add(new Deadline("assignemntsss",localDateTime));
        TaskList list = new TaskList(temp);
        Reminders.overdue(list);

        assertTrue(Reminders.exist());
    }
}
