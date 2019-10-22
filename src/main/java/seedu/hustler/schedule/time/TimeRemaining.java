package seedu.hustler.schedule.time;

import java.time.Duration;
import java.time.LocalDateTime;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Task;

/**
 * Class that gives the time remaining
 * for a task to be due.
 */
public class TimeRemaining {

    /**
     * Gives the time remaining
     * before an event is due.
     *
     * @param event event to be checked
     * @return amount of time remaining before it is due
     */
    public static Duration left(Event event) {
        return Duration.between(LocalDateTime.now(), event.getDateTime());
    }
    
    /**
     * Gives the amount of time remaining
     * before a deadline is due.
     *
     * @param deadline deadline to be checked
     * @return amount of time remaining before it is due.
     */
    public static Duration left(Deadline deadline) {
        return Duration.between(LocalDateTime.now(), deadline.getDateTime());
    }

    /**
     * Handles to the ToDo case.
     *
     * @param todo todo task to be checked.
     * @return 365 days by default
     */
    public static Duration left(ToDo todo) {
        return Duration.ofDays(14);
    }
    
    /**
     * Computes amount of time available before task is due.
     *
     * @param task task to be checked.
     * @return amount of time available (14 days in case of todo)
     */

    public static Duration left(Task task) {
        if (task instanceof ToDo) {
            return Duration.ofDays(14);
        } else {
            return Duration.between(LocalDateTime.now(), task.getDateTime());
        }

    }

}
