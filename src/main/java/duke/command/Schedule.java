package duke.command;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
    private Date time;
    private String task;
    private boolean ongoing;

    public Schedule(Date time, String toString) {
        this.time = time;
        this.task = toString;
        this.ongoing = false;
    }

    public Schedule(Date time, String toString, boolean ongoing) {
        this.time = time;
        this.task = toString;
        this.ongoing = ongoing;
    }

    public Date getStart() {
        return time;
    }

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
