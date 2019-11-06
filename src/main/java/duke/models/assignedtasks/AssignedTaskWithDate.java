//@@author WEIFENG-NUSCEG

package duke.models.assignedtasks;

import duke.util.DateTimeParser;
import duke.exceptions.DukeException;

import java.time.LocalDateTime;

/**
 * Represents a Assigned task that has been assigned to a patient with a deadline.
 */
public class AssignedTaskWithDate extends AssignedTask {

    /**
     * Create a new AssignedTask with its patient id, task id, deadline, and task type.
     *
     * @param pid patient id
     * @param tid task id
     * @param timeBeforeFormat deadline
     * @param type task type
     */
    public AssignedTaskWithDate(int pid, int tid, String timeBeforeFormat, String type) throws DukeException {
        super(pid, tid, type);
        setTodoDateRaw(timeBeforeFormat);
        try {
            setTodoDate(DateTimeParser.convertToLocalDateTime(timeBeforeFormat));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithDate.class, "The date time format is wrong!");
        }
    }

    /**
     * Create a new AssignedTask with its patient id, task id, deadline, and task type.
     *
     * @param pid patient id
     * @param tid task id
     * @param isdone is the task done
     * @param isrecurrsive is the task recursive
     * @param timeBeforeFormat deadline
     * @param type task type
     */
    public AssignedTaskWithDate(int pid, int tid, boolean isdone, boolean isrecurrsive,
                                String timeBeforeFormat, String type) throws DukeException {
        super(pid, tid, isdone, isrecurrsive, type);
        setTodoDateRaw(timeBeforeFormat);
        try {
            setTodoDate(DateTimeParser.convertToLocalDateTime(timeBeforeFormat));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithDate.class, "The date time format is wrong!");
        }
    }

    /**
     * Create a new AssignedTask with its patient id, task id, deadline, and task type.
     * @param pid patient id
     * @param tid task id
     * @param isdone is the task done
     * @param isrecurrsive is the task recursive
     * @param timeBeforeFormat deadline
     * @param type task type
     * @param uniqueId unique id of the task
     */
    public AssignedTaskWithDate(int pid, int tid, boolean isdone, boolean isrecurrsive,
                                String timeBeforeFormat, String type, int uniqueId) throws DukeException {
        super(pid, tid, isdone, isrecurrsive, type, uniqueId);
        setTodoDateRaw(timeBeforeFormat);
        try {
            setTodoDate(DateTimeParser.convertToLocalDateTime(timeBeforeFormat));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithDate.class, "The date time format is wrong!");
        }
    }

    /**
     * Return a string with the task status icon and the time after parsing.
     *
     * @return a string with the task information
     */
    public String toString() {
        return super.printStatus() + " " + DateTimeParser.convertToEnglishDateTimeBeforeParse(getTodoDate());
    }

}
