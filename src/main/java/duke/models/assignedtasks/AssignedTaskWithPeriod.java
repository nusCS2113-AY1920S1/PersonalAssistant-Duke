//@@author WEIFENG-NUSCEG

package duke.models.assignedtasks;

import duke.util.DateTimeParser;
import duke.exceptions.DukeException;

/**
 * Represents a Assigned task that has been assigned to a patient with a period.
 */
public class AssignedTaskWithPeriod extends AssignedTask {

    /**
     * Create a new AssignedTask with its patient id, task id, period, and task type.
     *
     * @param pid   patient id
     * @param tid   task id
     * @param stime starting time
     * @param etime ending time
     * @param type  task type
     */
    public AssignedTaskWithPeriod(int pid, int tid, String stime, String etime, String type) throws DukeException {
        super(pid, tid, type);
        setStartDateRaw(stime);
        setEndDateRaw(etime);

        try {
            setStartDate(DateTimeParser.convertToLocalDateTime(stime));
            setEndDate(DateTimeParser.convertToLocalDateTime(etime));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithPeriod.class, "The date time format is wrong!");
        }

        if (getEndDate().isBefore(getStartDate())) {
            throw new DukeException(AssignedTaskWithPeriod.class,
                    "You can't assign the end time earlier than the start time!");
        }
    }

    /**
     * Create a new AssignedTask with its patient id, task id, period, and task type, recursive status,
     * is task done status.
     *
     * @param pid          patient id
     * @param tid          task id
     * @param isDone       is task done
     * @param isRecurrsive is task recursive
     * @param stime        starting time
     * @param etime        ending time
     * @param type         task type
     */
    public AssignedTaskWithPeriod(int pid, int tid, boolean isDone,
                                  boolean isRecurrsive, String stime, String etime, String type) throws DukeException {
        super(pid, tid, isDone, isRecurrsive, type);
        setStartDateRaw(stime);
        setEndDateRaw(etime);

        try {
            setStartDate(DateTimeParser.convertToLocalDateTime(stime));
            setEndDate(DateTimeParser.convertToLocalDateTime(etime));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithPeriod.class, "The date time format is wrong!");
        }

        if (getEndDate().isBefore(getStartDate())) {
            throw new DukeException(AssignedTaskWithPeriod.class,
                    "You can't assign the end time earlier than the start time!");
        }

    }


    /**
     * Create a new AssignedTask with its patient id, task id, period, and task type, recursive status,
     * is task done status and unique id.
     *
     * @param pid          patient id
     * @param tid          task id
     * @param isDone       is task done
     * @param isRecurrsive is task recursive
     * @param stime        staring time
     * @param etime        ending time
     * @param type         task type
     * @param uniqueId     unique id
     */
    public AssignedTaskWithPeriod(int pid, int tid, boolean isDone,
                                  boolean isRecurrsive, String stime, String etime, String type, int uniqueId)
            throws DukeException {
        super(pid, tid, isDone, isRecurrsive, type, uniqueId);
        setStartDateRaw(stime);
        setEndDateRaw(etime);

        try {
            setStartDate(DateTimeParser.convertToLocalDateTime(stime));
            setEndDate(DateTimeParser.convertToLocalDateTime(etime));
        } catch (DukeException e) {
            throw new DukeException(AssignedTaskWithPeriod.class, "The date time format is wrong!");
        }

        if (getEndDate().isBefore(getStartDate())) {
            throw new DukeException(AssignedTaskWithPeriod.class,
                    "You can't assign the end time earlier than the start time!");
        }
    }

    /**
     * Return a string with the task status icon and the time after parsing.
     *
     * @return a string with the task information
     */
    public String toString() {
        return super.printStatus() + " From " + DateTimeParser
                .convertToEnglishDateTimeBeforeParse(getStartDate())
                + " To "
                + DateTimeParser.convertToEnglishDateTimeBeforeParse(getEndDate());
    }

}