package duchess.model.calendar;

import duchess.model.task.Task;

import java.time.LocalDate;

public class RequestProxy {
    private static AcademicYear academicYear;
    private static final LocalDate calendarStart;
    private static final LocalDate calendarEnd;

    static {
        academicYear = new AcademicYear();
        calendarStart = academicYear.getAcademicYearStart();
        calendarEnd = academicYear.getAcademicYearEnd();
    }

    private static boolean isValidDate(LocalDate key) {
        return key.compareTo(calendarStart) >= 0 && key.compareTo(calendarEnd) <= 0;
    }

    /**
     * Checks if task is an event and falls within the current
     * academic year.
     *
     * @param task Task to be deleted or added to duchessCalendar
     * @return true is task is an event and within the academic year
     */
    public static boolean isModifiable(Task task) {
        if (!task.getTimeFrame().hasDuration()) {
            return false;
        } else {
            LocalDate key = task.getTimeFrame().getStart().toLocalDate();
            return isValidDate(key);
        }
    }

    public static String getDateInformation(LocalDate date) {
        return academicYear.toString(date);
    }
}