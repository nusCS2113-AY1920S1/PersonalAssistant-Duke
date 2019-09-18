package duke.task;

import duke.parser.Parser;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//import static duke.task.Task.getDate;

public class ViewSchedules extends TaskList
{
    private Date when;
    public ViewSchedules(String when) {
        this.when = Parser.getDate(when);
    }

    public Date getCurrentDate() {
        return when;
    }
}
