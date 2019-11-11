package seedu.hustler.reminders;

import org.junit.jupiter.api.Test;
import seedu.hustler.task.TaskList;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.Reminders;
import seedu.hustler.task.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for reminders.
 */

public class RemindersTest {

    @Test

    /**
     * Check if overDue reminder exist.
     */
    public void testOverDue() {
        ArrayList<Task> temp = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2019,1,1,1,1,1);
        temp.add(new Deadline("assignment",localDateTime, "M", "CS",localDateTime));
        TaskList tempList = new TaskList(temp);
        Reminders.overdue(tempList);
        assertTrue(Reminders.exist());
    }
}
