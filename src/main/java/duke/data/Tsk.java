package duke.data;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Maintains the associations between task type characters and tasks (e.g. "T" -> ToDoTask). For use in parsing data
 * files.
 */
public enum Tsk {
    TODO("T") {
        public Task getTask(String[] taskArr) throws IndexOutOfBoundsException {
            return new ToDoTask(taskArr[3], getReminder(taskArr[2]));
        }
    },

    EVENT("E") {
        /**
         * Constructs an Event Task with 2 dates.
         * @param taskArr An array containing the data for the task, format specific to each task type.
         * @return new Event Task created with 2 dates and getReminder
         * @throws DateTimeParseException if the date is not in the correct format
         * @throws IndexOutOfBoundsException if there are fewer arguments than needed
         */
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            LocalDateTime datetime = LocalDateTime.parse(taskArr[4], TimedTask.getPatDatetime());
            LocalDateTime endDatetime = LocalDateTime.parse(taskArr[5], TimedTask.getPatDatetime());
            return new EventTask(taskArr[3], datetime, endDatetime, getReminder(taskArr[2]));
        }
    },
    FORP("F") {
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            Duration period = Duration.parse(taskArr[3]);
            return new FixedDurationTask(taskArr[2], period);
        }
    },
    BETWN("B") {
        /**
         * Constructs a task which must be done within a specified period.
         * @param taskArr An array containing the data for the task, format specific to each task type.
         * @return new DoWithinPeriodTask with 2 dates and a reminder
         * @throws DateTimeParseException if the date is not in the correct format
         * @throws IndexOutOfBoundsException if there is fewer than expected arguments
         */
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            LocalDateTime datetime = LocalDateTime.parse(taskArr[4], TimedTask.getPatDatetime());
            LocalDateTime endDatetime = LocalDateTime.parse(taskArr[5], TimedTask.getPatDatetime());
            return new DoWithinPeriodTask(taskArr[3], datetime, endDatetime, getReminder(taskArr[2]));
        }
    },
    DLINE("D") {
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            LocalDateTime datetime = LocalDateTime.parse(taskArr[4], TimedTask.getPatDatetime());
            return new DeadlineTask(taskArr[3], datetime, getReminder(taskArr[2]));
        }
    };

    private final String taskTypeStr;

    /**
     * Creates the Tsk enum instance and associates the specified character with it.
     *
     * @param taskTypeStr The string to be associated with the specified task type.
     */
    Tsk(final String taskTypeStr) {
        this.taskTypeStr = taskTypeStr;
    }

    private static Reminder getReminder(String remind) {
        Reminder reminder;
        try {
            reminder = new Reminder(LocalDateTime.parse(remind, TimedTask.getPatDatetime()));
        } catch (DateTimeParseException e) {
            reminder = null;
        }

        return reminder;
    }

    @Override
    public String toString() {
        return taskTypeStr;
    }

    /**
     * Creates and sets up a new task from an array containing the data it is to have. The parameters for the task
     * constructor are extracted by this method.
     *
     * @param taskArr An array containing the data for the task, format specific to each task type.
     * @return A task loaded with the data from taskArr.
     */
    public abstract Task getTask(String[] taskArr);
}
