package duke.models.assignedPatientTasks;

import duke.util.DateTimeParser;
import duke.exceptions.DukeException;

import java.time.LocalDateTime;

public class AssignedTaskWithDate extends AssignedTask {

    private LocalDateTime deadline;
    private String deadlineRaw;

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
        this.deadlineRaw = timeBeforeFormat;
        try {
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
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
        this.deadlineRaw = timeBeforeFormat;
        try {
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
        } catch (DukeException e) {
            throw new DukeException("The date time format is wrong!");
        }
    }

    /**
     *  .
     * @return .
     */
    public LocalDateTime getDeadline() {
        return  this.deadline;
    }

    /**
     *  .
     * @return .
     */
    public String getDeadlineRaw() {
        return this.deadlineRaw;
    }

    /**
     *  .
     * @param time .
     */
    public void updateDeadline(String time) {
        try {
            this.deadline = DateTimeParser.convertToLocalDateTime(time);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  .
     * @return .
     */
    public String toString() {
        return super.printStatus() + " " + DateTimeParser.convertToEnglishDateTimeBeforeParse(deadline);
    }

}
