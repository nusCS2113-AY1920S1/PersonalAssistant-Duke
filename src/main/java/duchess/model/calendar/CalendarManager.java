package duchess.model.calendar;

import duchess.model.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalendarManager extends RequestProxy {
    private List<CalendarEntry> currCalendar;
    private Task task;

    /**
     * Constructor for calendar manager. This class
     * modifies the duchessCalendar inside store.
     *
     * @param duchessCalendar duchessCalendar
     * @param task            Task to be added or deleted to duchessCalendar
     */
    public CalendarManager(List<CalendarEntry> duchessCalendar, Task task) {
        this.currCalendar = duchessCalendar;
        this.task = task;
    }

    /**
     * Deletes a task from duchessCalendar in the store.
     *
     * @return modified duchessCalendar
     */
    public List<CalendarEntry> deleteEntry() {
        if (isModifiable(task)) {
            this.currCalendar = currCalendar.stream()
                    .filter(entry -> entry.getDateTasks().remove(task))
                    .collect(Collectors.toList());
        }
        return this.currCalendar;
    }

    /**
     * Deletes a task from duchessCalendar in the store.
     *
     * @return modified duchessCalendar
     */
    public List<CalendarEntry> addEntry() {
        if (isModifiable(task)) {
            LocalDate taskDate = task.getTimeFrame().getStart().toLocalDate();
            Optional<CalendarEntry> oldEntry = this.currCalendar
                    .stream()
                    .filter(entry -> entry.isRequestedEntry(taskDate))
                    .findFirst();
            List<Task> newList;
            if (oldEntry.isPresent()) {
                newList = oldEntry.get().getDateTasks();
            } else {
                newList = new ArrayList<>();
            }
            newList.add(task);
            this.currCalendar.add(new CalendarEntry(taskDate, newList));
        }
        return this.currCalendar;
    }
}

