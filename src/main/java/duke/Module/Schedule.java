package duke.Module;

import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class manages the timetable for the user.
 */
public class Schedule {
    /**
     * Constructor for schedule.
     */
    public Schedule() {
        //do nothing
    }


    public String getMonth() {
        StringBuilder week = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("EEE dd MMM");
        for (int i = 0; i < numDays; i++) {
            week.append("[").append(i + 1).append("]. ").append(df.format(cal.getTime())).append("\n");
            cal.add(Calendar.DATE, 1);
        }
        return week.toString();
    }

    /**
     * Method will show the current days in the present week.
     *
     * @return List of all days in the week in the format [index] DAY DATE MONTH
     */
    public String getWeek() {
        StringBuilder week = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_WEEK);

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

    public String getDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        HashMap<Date, Integer> dailySchedule = new HashMap<>();
        DateFormat df = new SimpleDateFormat("HH:MM");
        for (int i = 0; i < 12; i++) {
            dailySchedule.put(cal.getTime(), i);
            cal.add(Calendar.HOUR, 1);
        }
        for (Date d: dailySchedule.keySet()) {
            System.out.println(d);
        }


        return "Map of the days events";
    }
}
