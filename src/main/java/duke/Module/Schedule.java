package duke.Module;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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

    /**
     * Array of all possible monthes
     */
    private String[] months = {
        "January", "February", "March",
        "April", "May", "June",
        "July", "August", "September",
        "October", "November", "December"
    };

    /**
     * Array of all days in each month
     */
    private int[] days = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    /**
     * Will print out a formatted calender.
     *
     * @param numberOfDays days in the month
     * @param startDay beginning day in the month
     */
    private static void printMonth(int numberOfDays, int startDay) {
        int weekdayIndex = 0;
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        for (int day = 1; day < startDay; day++) {
            System.out.print("    ");
            weekdayIndex++;
        }

        for (int day = 1; day <= numberOfDays; day++) {
            System.out.printf("%1$2d", day);
            weekdayIndex++;
            if (weekdayIndex == 7) {
                weekdayIndex = 0;
                System.out.println();
            } else {
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    /**
     * Function gets the month of the current year.
     *
     * @return String of all the days in the month
     */
    public String getMonth() {
        StringBuilder week = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int numDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Set the calendar to monday of the current week
        cal.set(Calendar.DAY_OF_MONTH, 1);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("MMM");
        System.out.println("");
        System.out.println(df.format(cal.getTime()) + " " + cal.get(Calendar.YEAR));
        printMonth(numDays, cal.get(Calendar.DAY_OF_MONTH));
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

    /**
     * Function gets all the hours in the selected day.
     * Will load events if events have been allocated.
     *
     * @param day The selected day of the month.
     * @return String of every hour from 8am inside the day.
     */
    public String getDay(int day) {
//        Date date = new Date();
//        date = new SimpleDateFormat("dd MM yy ").parse(day);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DATE, day);
        Map<Date, Integer> dailySchedule = new TreeMap<>();
        DateFormat df = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < 16; i++) {
            dailySchedule.put(cal.getTime(), i);
            cal.add(Calendar.HOUR, 1);
        }
        for (Date d : dailySchedule.keySet()) {
            System.out.println(df.format(d));
        }


        return "Map of the day's events";
    }
}
