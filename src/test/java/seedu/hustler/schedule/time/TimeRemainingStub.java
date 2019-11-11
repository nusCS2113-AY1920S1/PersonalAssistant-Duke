package seedu.hustler.schedule.time;

import java.time.Duration;
import java.time.LocalDateTime;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Task;

/**
 * Class that gives that gives time remaining starting from a fixed
 * time for testing.
 */
public class TimeRemainingStub implements Timing {

    public static LocalDateTime startTime = LocalDateTime.of(2019, 11, 1, 0, 0);

    /**
     * Computes amount of time available before task is due from
     * a fixed starting point.
     *
     * @param task task to be checked.
     * @return amount of time available (14 days in case of todo)
     */
    public Duration secondsLeft(Task task) {
        if (task instanceof ToDo) {
            return Duration.ofDays(14);
        } else {
            return Duration.between(startTime, task.getDateTime());
        }

    }

}
