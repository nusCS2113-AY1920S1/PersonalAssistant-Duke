package duchess.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class CalendarUtil {
    private static final LocalDate SEM_ONE_START;
    private static final LocalDate SEM_ONE_END;
    private static final LocalDate SEM_TWO_START;
    private static final LocalDate SEM_TWO_END;
    private static final String SEMESTER_ONE = "Semester 1";
    private static final String SEMESTER_TWO = "Semester 2";

    static {
        LocalDate today = LocalDate.now();
        LocalDate separator = adjustDate(today, 0, Month.AUGUST);
        if (today.compareTo(separator) >= 0) {
            SEM_ONE_START = separator;
            SEM_TWO_START = adjustDate(today, 1, Month.JANUARY);
        } else {
            SEM_ONE_START = adjustDate(today, -1, Month.AUGUST);
            SEM_TWO_START = adjustDate(today, 0, Month.JANUARY);
        }
        SEM_ONE_END = SEM_ONE_START.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusWeeks(16);
        SEM_TWO_END = SEM_TWO_START.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusWeeks(16);
    }

    /**
     * Adjusts the various input dates accordingly to input requirements.
     *
     * @param date  date to be adjusted
     * @param years num of years to take away or add to date
     * @param month month the date should be adjusted to
     * @return adjusted date
     */
    private static LocalDate adjustDate(LocalDate date, long years, Month month) {
        return date.plusYears(years).with(month).with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
    }

    /**
     * Process the period in the academic year at which the input date falls in.
     *
     * @param date input
     * @return -1 if date occurs outside of the current formal academic year, 1 and 2 for semester 1 and 2 respectively
     */
    public static int processDate(LocalDate date) {
        if (date.compareTo(SEM_ONE_START) >= 0 && date.compareTo(SEM_ONE_END) <= 0) {
            return 1;
        } else if (date.compareTo(SEM_TWO_START) >= 0 && date.compareTo(SEM_TWO_END) <= 0) {
            return 2;
        } else {
            return -1;
        }
    }

    /**
     * Processes the current academic year.
     *
     * @return the current academic year
     */
    private static String processYear() {
        return "AY" + SEM_ONE_START.getYear() + "/" + SEM_ONE_START.plusYears(1).getYear();
    }

    /**
     * Processes the semester a given date falls in.
     *
     * @param date input
     * @return either Semester 1 or Semester 2
     */
    private static String processSemester(LocalDate date) {
        int period = processDate(date);
        if (period == 1) {
            return SEMESTER_ONE;
        } else {
            return SEMESTER_TWO;
        }
    }

    /**
     * Processes the week information of a given date.
     *
     * @param date input
     * @return indicates the number of week, or whether the date falls on a special week
     */
    private static String processWeek(LocalDate date) {
        int currWeek = getWeekAsInt(date);
        if (currWeek == 7) {
            return "Recess Week";
        } else if (currWeek >= 8 && currWeek <= 14) {
            return "W" + (currWeek - 1);
        } else if (currWeek == 15) {
            return "Reading Week";
        } else if (currWeek == 16 || currWeek == 17) {
            return "Examinations";
        } else {
            return "W" + currWeek;
        }
    }

    /**
     * Returns the academic year, semester and week of a given date.
     *
     * @param date input
     * @return academic context
     */
    public static String toString(LocalDate date) {
        return processYear() + ", " + processSemester(date) + ", " + processWeek(date);
    }

    /**
     * Processes the semester a given date falls in.
     *
     * @param date input
     * @return returns the starting date of the input date's semester
     */
    private static LocalDate nearestSemStart(LocalDate date) {
        String semester = processSemester(date);
        if (semester.equals(SEMESTER_ONE)) {
            return SEM_ONE_START;
        } else {
            assert (semester.equals(SEMESTER_TWO));
            return SEM_TWO_START;
        }
    }

    /**
     * Processes the num of weeks into the semester of a given date.
     *
     * @param date input
     * @return num of weeks into the semester as of the input date
     */
    public static int getWeekAsInt(LocalDate date) {
        LocalDate comparison = nearestSemStart(date);
        final double week = 7.0;
        long daysBetween = ChronoUnit.DAYS.between(comparison, date) + 1;
        return (int) Math.ceil(daysBetween / week);
    }
}