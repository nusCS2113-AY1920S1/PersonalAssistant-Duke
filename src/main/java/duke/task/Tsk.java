package duke.task;


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
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            LocalDateTime datetime = LocalDateTime.parse(taskArr[4], TimedTask.getDataFormatter());
            return new EventTask(taskArr[3], datetime, getReminder(taskArr[2]));
        }
    },
    DLINE("D") {
        public Task getTask(String[] taskArr) throws DateTimeParseException, IndexOutOfBoundsException {
            LocalDateTime datetime = LocalDateTime.parse(taskArr[4], TimedTask.getDataFormatter());
            return new DeadlineTask(taskArr[3], datetime, getReminder(taskArr[2]));
        }
    };

    private final String taskChar;

    /**
     * Creates the Tsk enum instance and associates the specified character with it.
     *
     * @param taskChar The character to be associated with the specified task type.
     */
    Tsk(final String taskChar) {
        this.taskChar = taskChar;
    }

    private static Reminder getReminder(String remind) {
        Reminder reminder;
        try {
            reminder = new Reminder(LocalDateTime.parse(remind, TimedTask.getDataFormatter()));
        } catch (DateTimeParseException e) {
            reminder = null;
        }

        return reminder;
    }

    @Override
    public String toString() {
        return taskChar;
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
