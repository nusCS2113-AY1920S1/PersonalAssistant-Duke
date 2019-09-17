package Task;

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
        String hour =  new SimpleDateFormat("h").format(after);
        String min = new SimpleDateFormat("mm").format(after);
        String marker = new SimpleDateFormat("a").format(after);
        String day = new SimpleDateFormat("d").format(after);
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(after);
        String newDateFormat = TaskList.numOrdinal(Integer.parseInt(day)) + " of " + monthYear + ", " +
                hour + (min.equals("00") ? marker : ("." + min + marker));
        return newDateFormat;
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
