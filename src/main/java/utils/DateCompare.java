package utils;

import java.util.Calendar;
import java.util.Date;

public class DateCompare {

    /**
     * Checks if day2 lies inside of day1.
     *
     * @param day1 the whole day from 00:00 to 23:59:59
     * @param day2 the day we want to check if it is in day 1
     * @return true if day 2 is in day 1
     */
    public static boolean isSameDay(Date day1, Date day2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day1);
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.SECOND, -1);
        Date day1End = calendar.getTime();
        return day2.after(day1) && day2.before(day1End);
    }

    /**
     * Checks if 2 date ranges, A and B are overlapping each other.
     *
     * @param startDateA start of date A
     * @param endDateA   end of date A
     * @param startDateB start of date B
     * @param endDateB   end of date B
     * @return true if dates are overlapping else return false
     */
    public static boolean isOverlapping(Date startDateA, Date endDateA, Date startDateB, Date endDateB) {
        if (startDateA.after(startDateB) && startDateA.before(endDateB)) {
            return true;
        } else if (endDateA.after(startDateB) && endDateA.before(endDateB)) {
            return true;
        } else if (startDateA.after(startDateB) && endDateA.before(endDateB)) {
            return true;
        } else if (startDateB.after(startDateA) && startDateB.before(endDateA)) {
            return true;
        } else if (endDateB.after(startDateA) && endDateB.before(endDateB)) {
            return true;
        } else if (startDateB.after(startDateA) && endDateB.before(startDateB)) {
            return true;
        } else {
            return startDateA.equals(startDateB)
                || endDateA.equals(endDateB)
                || startDateA.equals(endDateB)
                || endDateA.equals(startDateB);
        }
    }
}
