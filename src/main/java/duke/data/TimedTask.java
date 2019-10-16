package duke.data;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class TimedTask extends Task implements Comparable<TimedTask> {
    private static final DateTimeFormatter PAT_DATETIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter PAT_DATETIME_DISPLAY = DateTimeFormatter.ofPattern("eee, d MMM yyyy h:mm a");

    private LocalDateTime time;

    TimedTask(String name, LocalDateTime time) {
        super(name);
        this.time = time;
    }

    TimedTask(String name, LocalDateTime time, Reminder reminder) {
        super(name, reminder);
        this.time = time;
    }

    public static DateTimeFormatter getPatDatetime() {
        return PAT_DATETIME;
    }

    public static DateTimeFormatter getPatDatetimeDisplay() {
        return PAT_DATETIME_DISPLAY;
    }

    @Override
    public String toData() {
        return super.toData() + "\t" + time.format(PAT_DATETIME);
    }

    protected String getTime() throws DateTimeException {
        return time.format(PAT_DATETIME_DISPLAY);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getDateTime() {
        return time;
    }

    @Override
    public int compareTo(TimedTask o) {
        return getDateTime().compareTo(o.getDateTime());
    }

    @Override
    public void changeTime(LocalDateTime newTime) {
        time = newTime;
    }

    // --Commented out by Inspection START (03/09/2019 11:57):
    //    public static DateTimeFormatter getDisplayFormatter() {
    //        return PAT_DATETIME_DISPLAY;
    //    }
    // --Commented out by Inspection STOP (03/09/2019 11:57)
}
