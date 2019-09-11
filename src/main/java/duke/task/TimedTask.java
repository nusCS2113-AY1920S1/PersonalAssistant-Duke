package duke.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class TimedTask extends Task {

    private static final DateTimeFormatter PAT_DATETIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter PAT_DATETIME_DISPLAY = DateTimeFormatter.ofPattern("eee, d MMM yyyy h:mm a");

    private LocalDateTime time;

    TimedTask(String _name, LocalDateTime _time) {
        super(_name);
        time = _time;
    }

   @Override
    public String toData() {
        return super.toData() + "\t" + time.format(PAT_DATETIME);
    }

    String getTime() throws DateTimeException {
        return time.format(PAT_DATETIME_DISPLAY);
    }

    public void setTime(LocalDateTime _time) {
        time = _time;
    }

    public static DateTimeFormatter getDataFormatter() {
        return PAT_DATETIME;
    }

// --Commented out by Inspection START (03/09/2019 11:57):
//    public static DateTimeFormatter getDisplayFormatter() {
//        return PAT_DATETIME_DISPLAY;
//    }
// --Commented out by Inspection STOP (03/09/2019 11:57)
}
