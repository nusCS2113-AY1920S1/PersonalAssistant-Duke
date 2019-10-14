package duchess.model.calendar;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class CalendarManager {
    private static Map<LocalDate, CalendarEntry> duchessCalendar;
    private static final LocalDate calendarStart;
    private static final LocalDate calendarEnd;
    private Map<LocalDate, CalendarEntry> view;

    static {
        AcademicContext academicContext = new AcademicContext(LocalDate.now());
        calendarStart = academicContext.getAcademicStartDate();
        calendarEnd = academicContext.getAcademicEndDate();
    }

    public CalendarManager() {
        populateCalendar();
    }

    private void populateCalendar() {
        duchessCalendar = new TreeMap<>();
        for (LocalDate hashKey = calendarStart; hashKey.isAfter(calendarEnd); hashKey = hashKey.plusDays(1)) {
            CalendarEntry calendarEntry = new CalendarEntry();
            duchessCalendar.put(hashKey, calendarEntry);
        }
    }

    public static Map<LocalDate, CalendarEntry> getDuchessCalendar() {
        return duchessCalendar;
    }
}

