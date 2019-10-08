package duchess.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class AcademicContext {
    private LocalDate localDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String academicYear;
    private String academicWeek;
    private String semester;
    private static final double week = 7.0;

    /**
     * Set academic context based on given date.
     *
     * @param localDateTime date
     */
    public AcademicContext(LocalDateTime localDateTime) {
        this.localDate = localDateTime.toLocalDate();
        this.startDate = localDate;
        this.endDate = localDate;
        this.semester = processSemester();
        this.academicWeek = processAcademicWeek();
        this.academicYear = processAcademicYear();
    }

    /**
     * Returns start or end date of a semester.
     *
     * @param month Month semester starts in
     * @param end   Start or end of semester
     * @return LocalDate with year corresponding to constructor parameter localDateTime
     */
    private LocalDate processDate(Month month, boolean end) {
        LocalDate temp = localDate.with(month).with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
        if (end) {
            temp = temp.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusWeeks(16);
        }
        return temp;
    }

    private String processSemester() {
        LocalDate semOneStart = processDate(Month.AUGUST, false);
        LocalDate semOneEnd = processDate(Month.AUGUST, true);
        LocalDate semTwoStart = processDate(Month.JANUARY, false);
        LocalDate semTwoEnd = processDate(Month.JANUARY, true);
        boolean isSemesterOne = localDate.compareTo(semOneStart) >= 0 && localDate.compareTo(semOneEnd) <= 0;
        boolean isSemesterTwo = localDate.compareTo(semTwoStart) >= 0 && localDate.compareTo(semTwoEnd) <= 0;
        boolean isSummerBreak = localDate.isAfter(semTwoEnd) && localDate.isBefore(semOneStart);

        if (isSemesterOne) {
            this.startDate = semOneStart;
            return "Semester 1";
        } else if (isSemesterTwo) {
            this.startDate = semTwoStart;
            return "Semester 2";
        } else if (isSummerBreak) {
            this.endDate = semOneStart;
            return "Summer Break";
        }
        if (localDate.getMonth() == Month.DECEMBER) {
            this.endDate = semTwoStart.plusYears(1).with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY));
        } else {
            this.endDate = semTwoStart;
        }
        return "Winter Break";
    }

    private String processAcademicWeek() {
        boolean isSchoolTerm = semester.equals("Semester 1") || semester.equals("Semester 2");

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (isSchoolTerm) {
            daysBetween++;
        }
        int currWeek = (int) Math.ceil(daysBetween / week);
        if (isSchoolTerm) {
            switch (currWeek) {
            case 7:
                return "Recess Week";
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return "W" + (currWeek - 1);
            case 15:
                return "Reading Week";
            case 16:
            case 17:
                return "Examinations";
            default:
                return "W" + currWeek;
            }
        }
        if (currWeek == 1) {
            return "last week of break before a new semester starts";
        } else {
            return currWeek + " weeks left to the next semester";
        }
    }

    private String processAcademicYear() {
        int currYear = localDate.getYear();
        boolean isFirstHalf = semester.equals("Semester 1")
                || (semester.equals("Winter Break") && localDate.getMonth() == Month.DECEMBER);

        if (isFirstHalf) {
            return "AY" + currYear + "/" + (currYear + 1);
        } else {
            return "AY" + (currYear - 1) + "/" + currYear;
        }
    }

    public String getAcademicContext() {
        return academicYear + ", " + semester + ", " + academicWeek;
    }
}