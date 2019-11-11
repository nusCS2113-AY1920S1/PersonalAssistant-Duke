package seedu.hustler.schedule.time;

import java.time.Duration;
import java.time.LocalDateTime;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Task;

/**
 * An interface that handles timing remaining for a task.
 */

public interface Timing {

    /**
     * Gives the time remaining
     * before task is due.
     *
     * @param task the task
     * @return amount of time remaining before it is due
     */
    Duration secondsLeft(Task task);

}
