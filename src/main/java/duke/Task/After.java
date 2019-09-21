package duke.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class After extends item {
    protected Date after;

    public After(String info, Boolean status, String after) {
        super(info, status);
        super.setType("A");
        this.after = TaskList.dateConvert(after);
    }

    /**
     * This function takes the "after" data in the After class and converts it into the string output format
     *  Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        return TaskList.dateToStringFormat(after);
    }

    /**
     * Function gets the unformatted date of after
     *
     * @return after
     */
    @Override
    public Date getRawDate() {
        return this.after;
    }


    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + getDate() + ")";
    }
}
