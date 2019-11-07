package spinbox.entities;

import javafx.util.Pair;
import spinbox.DateTime;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.CalendarSelectorException;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private static final String MIDNIGHT = " 00:00";
    private static final String BEFORE_MIDNIGHT = " 23:59";
    DateTime startDate;
    DateTime endDate;
    int modifier;

    public Calendar(int modifier, String date) throws CalendarSelectorException {
        this.modifier = modifier;
        setDates(date);
    }

    private void setDates(String date) throws CalendarSelectorException {
        switch (modifier) {
        case 1:
            startDate = new DateTime(date + MIDNIGHT);
            endDate = new DateTime(date + BEFORE_MIDNIGHT);
            break;
        case 2:
            startDate = new DateTime(date + MIDNIGHT).getStartOfTheWeek();
            endDate = new DateTime(date + BEFORE_MIDNIGHT).getEndOfTheWeek();
            break;
        case 3:
            startDate = new DateTime(date + MIDNIGHT).getStartOfTheMonth();
            endDate = new DateTime(date + BEFORE_MIDNIGHT).getEndOfTheMonth();
            break;
        default:
            throw new CalendarSelectorException();
        }
    }

    public String getMonthString() {
        return startDate.getMonthString();
    }

    public String getYearString() {
        return startDate.getYearString();
    }

    public int getStartDateDay() {
        return this.startDate.getDayOfWeek();
    }

    public int getEndOfMonthDay() {
        return this.endDate.getDayOfMonth();
    }

    public List<Task> tasksInCalendar(TaskList taskList) {
        return taskList.viewListInterval(startDate, endDate);
    }

    /**
     * Iterates through the TaskList to find out
     * which task overlaps with the current month.
     * @param taskList TaskList that contains all tasks
     * @return tasks contained inside the current month
     */
    public List<Pair<Integer, List<Task>>> taskInCalendarByDayInMonth(TaskList taskList) {
        DateTime currentDate = startDate;
        List<Task> tempTaskList = null;
        List<Task> relevantTaskList = tasksInCalendar(taskList);
        List<Pair<Integer, List<Task>>> allocatedTaskList = new ArrayList<>();
        for (int dateCount = 0; dateCount < endDate.getDayOfMonth(); dateCount++) {
            tempTaskList = new ArrayList<>();
            for (Task task : relevantTaskList) {
                Schedulable schedulable = (Schedulable) task;
                if (schedulable.isOverlapping(currentDate.getStartOfDay(), currentDate.getEndOfDay())) {
                    tempTaskList.add(schedulable);
                }
            }
            allocatedTaskList.add(new Pair<>(dateCount + 1, tempTaskList));
            currentDate = currentDate.getNextDay();
        }
        return allocatedTaskList;
    }
}