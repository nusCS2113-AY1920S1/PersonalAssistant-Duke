import duke.exception.DukeException;
import duke.data.EventTask;
import duke.data.Reminder;
import duke.data.Task;
import duke.data.TimedTask;
import duke.data.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit class testing the abstract class Task.
 */
public class TaskTest {
    private LocalDateTime datetime;
    private Task todo;
    private Task event;

    /**
     * Initialise Tasks.
     */
    @BeforeEach
    public void initialise() {
        datetime = LocalDateTime.parse("18/09/2019 0200", TimedTask.getPatDatetime());
        todo = new ToDoTask("JUnit tests");
        event = new EventTask("tutorial", datetime, datetime);
    }

    /**
     * Tests setReminder() with a Task that has not been completed.
     * Expect the Reminder to be added successfully.
     *
     * @throws DukeException If the Reminder is to be added to a completed Task.
     */
    @Test
    public void testSetReminder_taskNotDone_success() throws DukeException {
        assertEquals(todo.toString(), "[T][N] JUnit tests");
        todo.setReminder(new Reminder(datetime));
        assertEquals(todo.toString(), "[T][N][R: Wed, 18 Sep 2019 2:00 AM] JUnit tests");
    }

    /**
     * Tests setReminder() with a Task that has already been completed.
     * Expect failure in the addition of the Reminder to the Task, resulting in an Exception being thrown.
     *
     * @throws DukeException If the Task is already completed but still calls markDone().
     */
    @Test
    public void testSetReminder_taskDone_exceptionThrown() throws DukeException {
        event.markDone();
        assertThrows(DukeException.class, () -> event.setReminder(new Reminder(datetime)));
    }
}
