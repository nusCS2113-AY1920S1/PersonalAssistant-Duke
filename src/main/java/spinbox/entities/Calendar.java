package spinbox.entities;

import javafx.util.Pair;
import spinbox.DateTime;
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

    /**
     * Check if the List of tasks is within the start and end date
     * of the Calendar.
     * @param taskList List of Task and their corresponding moduleCode.
     * @return List of Task and moduleCode that is within Calendar.
     */
    public List<Pair<String, Task>> tasksInCalendar(List<Pair<String, Task>> taskList) {
        Task currentTask;
        String currentModuleCode;
        List<Pair<String, Task>> output = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            currentTask = taskList.get(i).getValue();
            currentModuleCode = taskList.get(i).getKey();
            if (currentTask.isSchedulable()) {
                Schedulable task = (Schedulable) currentTask;
                if (task.isOverlapping(startDate, endDate)) {
                    output.add(new Pair<>(currentModuleCode, currentTask));
                }
            }
        }
        return output;
    }

    /**
     * Iterates through the TaskList to find out
     * which task overlaps with the current month.
     * @param taskList TaskList that contains all tasks
     * @return tasks contained inside the current month
     */
    public List<Pair<Integer, List<Pair<String, Task>>>> taskInCalendarByDayInMonth(List<Pair<String, Task>> taskList) {
        DateTime currentDate = startDate;
        List<Pair<String, Task>> tempTaskList = null;
        List<Pair<String, Task>> relevantTaskList = tasksInCalendar(taskList);
        List<Pair<Integer, List<Pair<String, Task>>>> allocatedTaskList = new ArrayList<>();
        for (int dateCount = 0; dateCount < endDate.getDayOfMonth(); dateCount++) {
            tempTaskList = new ArrayList<>();
            for (Pair item : relevantTaskList) {
                Task task = (Task) item.getValue();
                String moduleCode = (String) item.getKey();
                Schedulable schedulable = (Schedulable) task;
                if (schedulable.isOverlapping(currentDate.getStartOfDay(), currentDate.getEndOfDay())) {
                    tempTaskList.add(new Pair<>(moduleCode, task));
                }
            }
            allocatedTaskList.add(new Pair<>(dateCount + 1, tempTaskList));
            currentDate = currentDate.getNextDay();
        }
        return allocatedTaskList;
    }
}