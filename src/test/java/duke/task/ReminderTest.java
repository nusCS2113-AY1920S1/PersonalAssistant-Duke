package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import duke.dukeexception.DukeException;
import org.junit.jupiter.api.Test;

//@@author gervaiseang
/**
 * A test class to test the correctness of Reminder class.
 */

public class ReminderTest {
    @Test
    void testReminder() throws DukeException {
        Task task = new Deadline("finish homework", "13/11/2019 1000");
        TaskList items = new TaskList();
        items.add(task);
        Reminders reminders = new Reminders();
        reminders.getReminders(3, items);
        assertEquals("[D][X] finish homework (by: 13th of November 2019, 10AM)", task.toString());
    }
}