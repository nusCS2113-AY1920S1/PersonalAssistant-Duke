package duke.models.assignedtasks;

import duke.util.DateTimeParser;
import duke.exceptions.DukeException;

import java.time.LocalDateTime;

public class AssignedTaskWithDate extends AssignedTask {

    /**
     * .
     *
     * @param pid .
     * @param tid .
     * @param timeBeforeFormat .
     * @param type .
     */
    public AssignedTaskWithDate(int pid, int tid, String timeBeforeFormat, String type) throws DukeException {
        super(pid, tid, type);
        setTodoDateRaw(timeBeforeFormat);
        try {
            setTodoDate(DateTimeParser.convertToLocalDateTime(timeBeforeFormat));
        } catch (DukeException e) {
            throw new DukeException("The date time format is wrong!");
        }
    }

    /**
     *  .
     * @param pid .
     * @param tid .
     * @param isdone .
     * @param isrecurrsive .
     * @param timeBeforeFormat .
     * @param type .
     */
    public AssignedTaskWithDate(int pid, int tid, boolean isdone, boolean isrecurrsive,
                                String timeBeforeFormat, String type) throws DukeException {
        super(pid, tid, isdone, isrecurrsive, type);
        setTodoDateRaw(timeBeforeFormat);
        try {
            setTodoDate(DateTimeParser.convertToLocalDateTime(timeBeforeFormat));
        } catch (DukeException e) {
            throw new DukeException("The date time format is wrong!");
        }
    }

    /**
     *  .
     * @return .
     */
    public String toString() {
        return super.printStatus() + " " + DateTimeParser.convertToEnglishDateTimeBeforeParse(getTodoDate());
    }

}
