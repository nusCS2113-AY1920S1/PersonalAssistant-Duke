package duke.models.assignedtasks;

import duke.util.DateTimeParser;
import duke.exceptions.DukeException;

import java.time.Duration;
import java.time.LocalDateTime;

public class AssignedTaskWithPeriod extends AssignedTask {

    private long duration;

    /**
     * .
     *
     * @param pid   .
     * @param tid   .
     * @param stime .
     * @param etime .
     * @param type  .
     */
    public AssignedTaskWithPeriod(int pid, int tid, String stime, String etime, String type) throws DukeException {
        super(pid, tid, type);
        setStartDateRaw(stime);
        setEndDateRaw(etime);
        try {
            setStartDate(DateTimeParser.convertToLocalDateTime(stime));
            setEndDate(DateTimeParser.convertToLocalDateTime(etime));
        } catch (DukeException e) {
            throw new DukeException("The date time format is wrong!");
        }

        duration = Duration.between(getStartDate(), getEndDate()).toMillis();
    }

    /**
     * .
     *
     * @param pid          .
     * @param tid          .
     * @param isDone       .
     * @param isRecurrsive .
     * @param stime        .
     * @param etime        .
     * @param type         .
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
            throw new DukeException("The date time format is wrong!");
        }

        duration = Duration.between(getStartDate(), getEndDate()).toMillis();
    }

    /**
     * .
     *
     * @return .
     */
    public String toString() {
        return super.printStatus() + " From " + DateTimeParser
                .convertToEnglishDateTimeBeforeParse(getStartDate())
                + " To "
                + DateTimeParser.convertToEnglishDateTimeBeforeParse(getEndDate());
    }

}