package duchess.model.calendar;

import duchess.model.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {

    /**
     * Acts as a ArrayList.remove() method. Removes a calendar entry from the duchess calendar.
     *
     * @param ceList duchess calendar
     * @param key    date of calendar entry
     * @return edited duchess calendar
     */
    private static List<CalendarEntry> removeEntryFromList(List<CalendarEntry> ceList, LocalDate key) {
        return ceList.stream().filter(ce -> !ce.getDate().equals(key)).collect(Collectors.toList());
    }

    /**
     * Deletes a task from the task list of a calendar entry stored in the calendar.
     *
     * @param currCalendar current duchess calendar
     * @param task         event
     * @param key          starting date of event
     */
    public static List<CalendarEntry> deleteEntry(List<CalendarEntry> currCalendar, Task task, LocalDate key) {
        CalendarEntry toModify = findEntry(currCalendar, key);
        List<Task> newList = toModify.getDateTasks()
                .stream()
                .filter(t -> !t.getTimeFrame().fallsWithin(task.getTimeFrame()))
                .collect(Collectors.toList());
        List<CalendarEntry> update = removeEntryFromList(currCalendar, key);
        update.add(new CalendarEntry(key, newList));
        return update;
    }

    /**
     * Adds a task to the task list of a calendar entry stored in the duchess calendar.
     *
     * @param currCalendar current duchess calendar
     * @param task         event
     * @param date         starting date of event
     */
    public static void addEntry(List<CalendarEntry> currCalendar, Task task, LocalDate date) {
        CalendarEntry update = findEntry(currCalendar, date);
        List<Task> newList = update.getDateTasks();
        if (newList.size() > 0) {
            currCalendar = removeEntryFromList(currCalendar, date);
        }
        newList.add(task);
        currCalendar.add(update);
    }

    /**
     * Finds a calendar entry in the calendar.
     *
     * @param currCalendar current calendar
     * @param start        desired date of the entry
     * @return calendar entry
     */
    public static CalendarEntry findEntry(List<CalendarEntry> currCalendar, LocalDate start) {
        return currCalendar
                .stream()
                .filter(e -> e.getDate().equals(start))
                .findFirst()
                .orElse(new CalendarEntry(start, new ArrayList<>()));
    }

    /**
     * Finds the calendar entries within a given start and end date.
     *
     * @param currCalendar current calendar
     * @param start        start date
     * @param end          end date
     * @return filtered list of calendar entries
     */
    public static List<CalendarEntry> findEntries(List<CalendarEntry> currCalendar, LocalDate start, LocalDate end) {
        return currCalendar
                .stream()
                .filter(ce -> ce.getDate().compareTo(start) >= 0 && ce.getDate().compareTo(end) <= 0)
                .collect(Collectors.toList());
    }
}

