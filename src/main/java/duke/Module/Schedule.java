package duke.Module;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class manages the timetable for the user.
 */
public class Schedule {

    public Schedule() {

    }

    /**
     * Method will show the current days in the present week.
     * @return List of all days in the week in the format [index] DAY DATE MONTH
     */
    public String getWeek() {
        int numDays = 7;
        StringBuilder week = new StringBuilder();
        Calendar cal = Calendar.getInstance();

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("EEE dd MMM");
        for (int i = 0; i < numDays; i++) {
            week.append("[").append(i + 1).append("]. ").append(df.format(cal.getTime())).append("\n");
            cal.add(Calendar.DATE, 1);
        }
        return week.toString();
    }
}
