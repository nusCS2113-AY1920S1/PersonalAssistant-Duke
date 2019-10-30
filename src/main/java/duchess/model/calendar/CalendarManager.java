package duchess.model.calendar;

import duchess.model.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CalendarManager extends RequestProxy {
    private static final int numOfDays = 7;

    /**
     * Deletes a task from duchessCalendar in the store.
     *
     * @return modified duchessCalendar
     */
    public static List<CalendarEntry> deleteEntry(List<CalendarEntry> currCalendar, Task task) {
        if (isModifiable(task)) {
            LocalDate key = task.getTimeFrame().getStart().toLocalDate();
            CalendarEntry toModify = currCalendar.stream()
                    .filter(ce -> ce.getDate().equals(key))
                    .findAny()
                    .orElse(null);
            assert (toModify != null);
            List<Task> newList = toModify.getDateTasks();
            newList.remove(task);
            currCalendar.remove(toModify);
            currCalendar.add(new CalendarEntry(key, newList));
        }
        return currCalendar;
    }

    /**
     * Deletes a task from duchessCalendar in the store.
     *
     * @return modified duchessCalendar
     */
    public static List<CalendarEntry> addEntry(List<CalendarEntry> currCalendar, Task task) {
        if (isModifiable(task)) {
            LocalDate taskDate = task.getTimeFrame().getStart().toLocalDate();
            Optional<CalendarEntry> oldEntry = currCalendar
                    .stream()
                    .filter(entry -> entry.isRequestedEntry(taskDate))
                    .findFirst();
            List<Task> newList;
            if (oldEntry.isPresent()) {
                newList = oldEntry.get().getDateTasks();
                currCalendar.remove(oldEntry.get());
            } else {
                newList = new ArrayList<>();
            }
            newList.add(task);
            currCalendar.add(new CalendarEntry(taskDate, newList));
        }
        return currCalendar;
    }

    /**
     * Returns an array of size seven (for the days in a week).
     * Array contains strings of either null value or description
     * of an event-type task.
     *
     * @param flatCalendar duchessCalendar
     * @param time         key in treeMap storing flatCalendar
     * @param str          description or null string
     * @param i            the day of week being processed
     * @return string array of size seven
     */
    private static String[] processArr(SortedMap<LocalTime, String[]> flatCalendar,
                                       LocalTime time,
                                       String str,
                                       int i) {
        String[] strArr;
        if (!flatCalendar.containsKey(time)) {
            strArr = new String[numOfDays];
        } else {
            strArr = flatCalendar.get(time);
        }
        strArr[i] = str;
        return strArr;
    }

    /**
     * Flat-maps the duchessCalendar into a treeMap for easier
     * printing of the calendar.
     *
     * @param currCalendar duchessCalendar
     * @param start        start date of calendar view
     * @param end          end date of calendar view
     * @return treeMap of desired calendar view
     */
    public static SortedMap<LocalTime, String[]> flatCalendar(List<CalendarEntry> currCalendar,
                                                              LocalDate start,
                                                              LocalDate end) {
        List<CalendarEntry> query = currCalendar
                .stream()
                .filter(ce -> ce.getDate().compareTo(start) >= 0 && ce.getDate().compareTo(end) <= 0)
                .collect(Collectors.toList());
        SortedMap<LocalTime, String[]> flatCalendar = new TreeMap<>();
        for (CalendarEntry ce : query) {
            List<Task> taskList = ce.getDateTasks();
            int index = ce.getDate().getDayOfWeek().getValue() - 1;
            for (Task t : taskList) {
                LocalTime time = t.getTimeFrame().getStart().toLocalTime();
                String description = t.toString();
                String[] updateArr = processArr(flatCalendar, time, description, index);
                flatCalendar.put(time, updateArr);
            }
        }
        return flatCalendar;
    }
}

