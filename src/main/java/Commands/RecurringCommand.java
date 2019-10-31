package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import Tasks.Assignment;
import Tasks.Event;
import Tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the command to add recurring tasks to the events TaskList.
 */
public class RecurringCommand extends Command {
    private final String description;
    private final String startTimeString;
    private final String endTimeString;
    private final String endDateString;
    private boolean isBiweekly;
    private boolean isRecur;
    private String startDateString;

    /**
     * Creates RecurringCommand object.
     * @param description Description of a task
     * @param startDateString Start date of a task
     * @param endDateString End date of a task
     * @param startTimeString Start time of a task
     * @param endTimeString End time of a task
     * @param isBiweekly whether the task is biweekly
     * @param isRecur whether the task needs to be added
     */
    public RecurringCommand(String description, String startDateString, String endDateString, String startTimeString, String endTimeString, boolean isBiweekly, boolean isRecur) {
        this.description = description;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        this.isBiweekly = isBiweekly;
        this.isRecur = isRecur;
    }

    private Date getNextWeekDate (Date inDate) {
        Date nextWeek = new Date(inDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        return nextWeek;
    }

    private Date getFollowingWeekDate (Date inDate) {
        Date followingWeek = new Date(inDate.getTime() + 14 * 24 * 60 * 60 * 1000);
        return followingWeek;
    }

    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);
        String oldStartDateString = startDateString;
        Date startOfFollowingWeek;
        Date startOfNextWeek;

        if (isRecur && isBiweekly) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                events.addTask(task);
                startOfFollowingWeek = getFollowingWeekDate(startDate);
                startDateString = dateFormat.format(startOfFollowingWeek);
                startDate = startOfFollowingWeek;
            }
            while (startOfFollowingWeek.before(endDate) || startOfFollowingWeek.equals(endDate));
        } else if (isRecur) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                events.addTask(task);
                startOfNextWeek = getNextWeekDate(startDate);
                startDateString = dateFormat.format(startOfNextWeek);
                startDate = startOfNextWeek;
            }
            while (startOfNextWeek.before(endDate) || startOfNextWeek.equals(endDate));
        } else if (isBiweekly) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                events.removeTask(task);
                startOfFollowingWeek = getFollowingWeekDate(startDate);
                startDateString = dateFormat.format(startOfFollowingWeek);
                startDate = startOfFollowingWeek;
            }
            while (startOfFollowingWeek.before(endDate) || startOfFollowingWeek.equals(endDate));
        } else {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                events.removeTask(task);
                startOfNextWeek = getNextWeekDate(startDate);
                startDateString = dateFormat.format(startOfNextWeek);
                startDate = startOfNextWeek;
            }
            while (startOfNextWeek.before(endDate) || startOfNextWeek.equals(endDate));
        }
        storage.updateEventList(events);
        return ui.showRecurring(description, oldStartDateString, endDateString, isBiweekly, isRecur);
    }
}
