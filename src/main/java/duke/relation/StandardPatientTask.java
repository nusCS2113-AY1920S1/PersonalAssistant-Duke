package duke.relation;

import duke.core.DateTimeParser;
import duke.core.DukeException;

import java.time.LocalDateTime;

public class StandardPatientTask extends PatientTask {

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
    public StandardPatientTask(int pid, int tid, String timeBeforeFormat, String type) {
        super(pid, tid, type);
        this.deadlineRaw = timeBeforeFormat;
        try {
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
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
    public StandardPatientTask(int pid, int tid,
                               boolean isdone, boolean isrecurrsive, String timeBeforeFormat, String type) {
        super(pid, tid, isdone, isrecurrsive, type);
        this.deadlineRaw = timeBeforeFormat;
        try {
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
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
