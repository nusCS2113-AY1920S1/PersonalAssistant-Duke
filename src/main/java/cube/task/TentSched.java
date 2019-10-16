//@@author LL-Pengfei
/**
 * Provide the functionality of enabling tentative scheduling
 * Input Format: tentative-scheduling description time1 time2 time3 time4 ... /at default_time_0
 */
package cube.task;

import cube.util.Parser;

import java.util.Date;

/**
 * The Class TentSched, describing attributes related to a tentative scheduled task
 */
public class TentSched extends Task{
    private String tentsched;

    /**
     * The Default Constructor for TentSched
     *
     * @param description The description of the tentatively scheduled task.
     * @param tentsched All the back-up tentative dates and times.
     * @param date The default date of the tentatively scheduled task.
     */
    public TentSched(String description, String tentsched, Date date) {
        super(description, date);
        this.tentsched = tentsched;
    }

    /**
     * Cast all information related to a tentatively scheduled task into one string.
     *
     * @return The string that contains all information related to a tentatively scheduled task.
     */
    @Override
    public String toString() {
        return "[TS]" + super.toString() + " (default set at: " + Parser.parseDateToString(date)
                                         + ") with tentative scheduled dates as: " + tentsched;
    }
}