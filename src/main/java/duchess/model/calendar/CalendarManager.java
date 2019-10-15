package duchess.model.calendar;

import duchess.model.TimeFrame;
import duchess.model.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class CalendarManager {
    private static SortedMap<LocalDate, List<Task>> duchessCalendar;
    private static final LocalDate calendarStart;
    private static final LocalDate calendarEnd;
    private static AcademicYear academicYear; // not a local variable -> need for parse calendar as flatMap later

    static {
        academicYear = new AcademicYear();
        calendarStart = academicYear.getAcademicYearStart();
        calendarEnd = academicYear.getAcademicYearEnd();
    }

    /**
     * This is the constructor for CalendarManager object.
     * The duchessCalendar is initialised here with key values
     * corresponding to the days in the current academic year.
     */
    public CalendarManager() {
        duchessCalendar = new TreeMap<>();
        for (LocalDate key = calendarStart; key.compareTo(calendarEnd) <= 0; key = key.plusDays(1)) {
            duchessCalendar.put(key, null);
        }
    }

    /**
     * Adds a calendar entry to the duchessCalendar.
     * For now, all calendar entries are events.
     * Calendar entries will be extended to include lessons in V1.3.
     *
     * @param timeFrame TimeFrame of event
     * @param task      Event
     */
    public void addEntry(TimeFrame timeFrame, Task task) {
        LocalDate dateKey = timeFrame.getStart().toLocalDate();
        boolean isValidEntry = dateKey.compareTo(calendarStart) >= 0 && dateKey.compareTo(calendarEnd) <= 0;

        if (isValidEntry) {
            List<Task> taskList = duchessCalendar.get(dateKey);
            if (taskList == null) {
                taskList = new ArrayList<>();
            }
            taskList.add(task);
            duchessCalendar.put(dateKey, taskList);
        }
    }
}

