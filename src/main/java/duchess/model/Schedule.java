package duchess.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private Date time;
    private String task;
    private boolean ongoing;

    /**
     * Constructor for schedule.
     *
     * @param time time to be shown in timetable
     * @param toString task details
     */
    public Schedule(Date time, String toString) {
        this.time = time;
        this.task = toString;
        this.ongoing = false;
    }

    /**
     * Constructor for schedule. Tracks ongoing events.
     *
     * @param time time to be shown in timetable
     * @param toString task details
     * @param ongoing event ongoing
     */
    public Schedule(Date time, String toString, boolean ongoing) {
        this.time = time;
        this.task = toString;
        this.ongoing = ongoing;
    }

    public Date getStart() {
        return time;
    }

    /**
     * Change Date to String.
     *
     * @return string containing time of task
     */
    public String getStartString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        formatter.setLenient(false);
        return formatter.format(time);
    }

    public String getTask() {
        return task;
    }

    public boolean getOngoing() {
        return ongoing;
    }
}
