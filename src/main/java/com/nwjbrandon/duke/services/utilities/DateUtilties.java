package com.nwjbrandon.duke.services.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateUtilties {

    /**
     * Check if two dates falls on the same day.
     * @param firstDate first date to check.
     * @param secondDate second date to check.
     * @return true if both dates falls on the same day.
     */
    public static boolean isSameDay(Date firstDate, Date secondDate) {
        return isDateSame(toCalendar(firstDate), toCalendar(secondDate));
    }

    /**
     * Check if the dates falls on the same day.
     * @param c1 first date in Calendar format.
     * @param c2 second date in Calendar format.
     * @return true if two calendar dates falls on the same day.
     */
    private static boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Converts date to calendar type.
     * @param date date in Date format.
     * @return date in Calendar format.
     */
    private static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
