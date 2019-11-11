package duchess.model.calendar;

import duchess.model.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        CalendarEntry toModify = currCalendar
                .stream()
                .filter(ce -> ce.getDate().equals(key))
                .findAny()
                .orElse(null);
        assert (toModify != null);
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
        Optional<CalendarEntry> oldEntry = currCalendar
                .stream()
                .filter(entry -> entry.isRequestedEntry(date))
                .findFirst();
        List<Task> newList;
        if (oldEntry.isPresent()) {
            CalendarEntry ce = oldEntry.get();
            newList = ce.getDateTasks();
            currCalendar = removeEntryFromList(currCalendar, date);
        } else {
            newList = new ArrayList<>();
        }
        newList.add(task);
        currCalendar.add(new CalendarEntry(date, newList));
    }
}

