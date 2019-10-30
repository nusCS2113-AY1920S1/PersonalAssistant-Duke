package duchess.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class AcademicYear {
    private static final LocalDate semOneStart;
    private static final LocalDate semOneEnd;
    private static final LocalDate semTwoStart;
    private static final LocalDate semTwoEnd;
    private static final LocalDate ayEnd;

    static {
        LocalDate today = LocalDate.now();
        LocalDate separator = adjustDate(today, 0, Month.AUGUST);
        if (today.compareTo(separator) >= 0) {
            semOneStart = separator;
            semTwoStart = adjustDate(today, 1, Month.JANUARY);
        } else {
            semOneStart = adjustDate(today, -1, Month.AUGUST);
            semTwoStart = adjustDate(today, 0, Month.JANUARY);
        }
        semOneEnd = semOneStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusWeeks(16);
        semTwoEnd = semTwoStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusWeeks(16);
        ayEnd = adjustDate(semTwoEnd, 0, Month.AUGUST).minusDays(1);
    }

    private static LocalDate adjustDate(LocalDate date, long years, Month month) {
        return date.plusYears(years).with(month).with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
    }

    private String getWeek(LocalDate comparison, LocalDate date) {
        int currWeek = getWeekAsInt(comparison, date);
        if (currWeek == 7) {
            return ", Recess Week";
        } else if (currWeek >= 8 && currWeek <= 14) {
            return ", W" + (currWeek - 1);
        } else if (currWeek == 15) {
            return ", Reading Week";
        } else if (currWeek == 16 || currWeek == 17) {
            return ", Examinations";
        } else {
            return ", W" + currWeek;
        }
    }

    /**
     * Checks if a given date falls within the current academic calendar.
     *
     * @param date date
     * @return true if date falls within the current academic calendar
     */
    public static boolean isWithinAcademicCalendar(LocalDate date) {
        return date.compareTo(semOneStart) >= 0 && date.compareTo(ayEnd) <= 0;
    }


    /**
     * Returns true if currWeek falls within semester break
     * and false if currWeek does not fall within semester break.
     *
     * @param currWeek week of interest
     * @return boolean showing if currWeek falls within semester break
     */
    public boolean isSemesterBreak(int currWeek) {
        if (currWeek == 7 || currWeek >= 15) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a week as integer value within a school semester.
     *
     * @param comparison comparison date
     * @param date       interested date
     * @return integer value representing week
     */
    public int getWeekAsInt(LocalDate comparison, LocalDate date) {
        final double week = 7.0;
        long daysBetween = ChronoUnit.DAYS.between(comparison, date) + 1;
        return (int) Math.ceil(daysBetween / week);
    }

    private String processInformation(LocalDate date) {
        boolean isSemesterOne = isFirstSemester(date);
        boolean isSemesterTwo = isSecondSemester(date);
        boolean isSchoolTerm = isSemesterOne || isSemesterTwo;
        String str = "AY" + semOneStart.getYear() + "/" + semOneStart.plusYears(1).getYear();
        LocalDate comparison = null;
        if (isSemesterOne) {
            str += ", Semester 1";
            comparison = semOneStart;
        } else if (isSemesterTwo) {
            str += ", Semester 2";
            comparison = semTwoStart;
        } else {
            str += ", Break";
        }
        if (isSchoolTerm) {
            str += getWeek(comparison, date);
        }
        return str;
    }

    public String toString(LocalDate date) {
        return processInformation(date);
    }

    public LocalDate getAcademicYearStart() {
        return semOneStart;
    }

    public LocalDate getAcademicYearEnd() {
        return ayEnd;
    }

    /**
     * Returns boolean showing if date falls within an academic semester.
     *
     * @param date interested date
     * @return boolean showing if date is within an academic semester
     */
    public boolean isAcademicSemester(LocalDate date) {
        boolean isSemesterOne = isFirstSemester(date);
        boolean isSemesterTwo = isSecondSemester(date);
        return isSemesterOne || isSemesterTwo;
    }

    // jk
    public boolean isFirstSemester(LocalDate date) {
        return date.compareTo(semOneStart) >= 0 && date.compareTo(semOneEnd) <= 0;
    }

    // jk
    public boolean isSecondSemester(LocalDate date) {
        return date.compareTo(semTwoStart) >= 0 && date.compareTo(semTwoEnd) <= 0;
    }

    // jk
    public LocalDate getSemOneStart() {
        return semOneStart;
    }

    // jk
    public LocalDate getSemTwoStart() {
        return semTwoStart;
    }
}