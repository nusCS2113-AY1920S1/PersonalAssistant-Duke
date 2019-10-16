package duke.task;

import duke.parser.Convert;
import java.util.Date;

/**
 * One of the B-Extensions.
 * @author x3chillax
 */
public class ViewSchedules extends TaskList {
    private Date when;

    public ViewSchedules(String w) {
        when = Convert.stringToDate(w);
    }

    public Date getCurrentDate() {
        return when;
    }
}
