package duke.relation;

import duke.core.DateTimeParser;
import duke.core.DukeException;

import java.time.Duration;
import java.time.LocalDateTime;

public class EventPatientTask extends PatientTask {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startTimeRaw;
    private String endTimeRaw;
    private long duration;

    /**
     * .
     *
     * @param pid   .
     * @param tid   .
     * @param stime .
     * @param etime .
     * @param type  .
     * @param uid   .
     */
    public EventPatientTask(int pid, int tid, String stime, String etime, String type, int uid) {
        super(pid, tid, type, uid);
        this.startTimeRaw = stime;
        this.endTimeRaw = etime;
        try {
            this.startTime = DateTimeParser.convertToLocalDateTime(stime);
            this.endTime = DateTimeParser.convertToLocalDateTime(etime);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        duration = Duration.between(startTime, endTime).toMillis();
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
     * @param uid          .
     */
    public EventPatientTask(int pid, int tid, boolean isDone,
                            boolean isRecurrsive, String stime, String etime, String type, int uid) {
        super(pid, tid, isDone, isRecurrsive, type, uid);
        this.startTimeRaw = stime;
        this.endTimeRaw = etime;
        try {
            this.startTime = DateTimeParser.convertToLocalDateTime(stime);
            this.endTime = DateTimeParser.convertToLocalDateTime(etime);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        duration = Duration.between(startTime, endTime).toMillis();
    }

    /**
     * .
     *
     * @return .
     */
    public String getStartTimeRaw() {
        return startTimeRaw;
    }

    /**
     * .
     *
     * @return .
     */
    public String getEndTimeRaw() {
        return endTimeRaw;
    }

    /**
     * .
     *
     * @return .
     */
    public LocalDateTime getStartTime(){return startTime;}

    /**
     * .
     *
     * @return .
     */
    public LocalDateTime getEndTime() {return endTime;}

    /**
     * .
     *
     * @param time .
     */
    public void updateStartTime(String time) {
        try {
            this.startTime = DateTimeParser.convertToLocalDateTime(time);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * .
     *
     * @param time .
     */
    public void updateEndTime(String time) {
        try {
            this.endTime = DateTimeParser.convertToLocalDateTime(time);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * .
     *
     * @return .
     */
    public long retrieveDuration() {
        return this.duration;
    }

    /**
     * .
     *
     * @return .
     */
    public String toString() {
        return super.printStatus() + " From " + DateTimeParser
                .convertToEnglishDateTimeBeforeParse(startTime)
                + "To"
                + DateTimeParser.convertToEnglishDateTimeBeforeParse(endTime);
    }

}