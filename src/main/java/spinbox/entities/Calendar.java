package spinbox.entities;

import spinbox.DateTime;
import spinbox.containers.lists.TaskList;
import spinbox.exceptions.CalendarSelectorException;

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

    public List<String> tasksInCalendar(TaskList taskList) {
        return taskList.viewListInterval(startDate, endDate);
    }
}