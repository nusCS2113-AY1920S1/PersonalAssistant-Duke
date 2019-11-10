package Commands;

import Commons.DukeConstants;
import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.Event;
import Tasks.TaskList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int MILLISECONDS = 1000;
    private static final int ONE_WEEK = 7;
    private static final int TWO_WEEKS = 14;
    ArrayList<String> eventConflict;

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
    public RecurringCommand(String description, String startDateString,
                            String endDateString, String startTimeString,
                            String endTimeString, boolean isBiweekly, boolean isRecur) {
        this.description = description;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        this.isBiweekly = isBiweekly;
        this.isRecur = isRecur;
    }

    /**
     * This method gets the date of 7 days later.
     * @param inDate date
     * @return date of 7 days later
     */
    private Date getNextWeekDate(Date inDate) {
        Date nextWeek = new Date(inDate.getTime() + ONE_WEEK * HOURS * MINUTES * SECONDS * MILLISECONDS);
        return nextWeek;
    }

    /**
     * This method gets the date of 14 days later.
     * @param inDate date
     * @return date of 14 days later
     */
    private Date getFollowingWeekDate(Date inDate) {
        Date followingWeek = new Date(inDate.getTime() + TWO_WEEKS * HOURS * MINUTES * SECONDS * MILLISECONDS);
        return followingWeek;
    }

    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception {
        Date startDate = DukeConstants.DAY_DATE_FORMAT.parse(startDateString);
        Date endDate = DukeConstants.DAY_DATE_FORMAT.parse(endDateString);
        String oldStartDateString = startDateString;
        Date startOfFollowingWeek;
        Date startOfNextWeek;
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        ArrayList<Assignment> temp = new ArrayList<>();

        if (isRecur && isBiweekly) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                eventConflict = super.checkEventConflict(events, task);
                if (!eventConflict.isEmpty()) {
                    break;
                }
                temp.add(task);
                startOfFollowingWeek = getFollowingWeekDate(startDate);
                startDateString = DukeConstants.DAY_DATE_FORMAT.format(startOfFollowingWeek);
                startDate = startOfFollowingWeek;
            } while (startOfFollowingWeek.before(endDate) || startOfFollowingWeek.equals(endDate));
        } else if (isRecur) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                eventConflict = super.checkEventConflict(events, task);
                if (!eventConflict.isEmpty()) {
                    break;
                }
                temp.add(task);
                startOfNextWeek = getNextWeekDate(startDate);
                startDateString = DukeConstants.DAY_DATE_FORMAT.format(startOfNextWeek);
                startDate = startOfNextWeek;
            } while (startOfNextWeek.before(endDate) || startOfNextWeek.equals(endDate));
        } else if (isBiweekly) {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                super.insideMapChecker(eventMap, task);
                temp.add(task);
                startOfFollowingWeek = getFollowingWeekDate(startDate);
                startDateString = DukeConstants.DAY_DATE_FORMAT.format(startOfFollowingWeek);
                startDate = startOfFollowingWeek;
            } while (startOfFollowingWeek.before(endDate) || startOfFollowingWeek.equals(endDate));
        } else {
            do {
                Assignment task = new Event(description, startDateString, startTimeString, endTimeString);
                super.insideMapChecker(eventMap, task);
                temp.add(task);
                startOfNextWeek = getNextWeekDate(startDate);
                startDateString = DukeConstants.DAY_DATE_FORMAT.format(startOfNextWeek);
                startDate = startOfNextWeek;
            } while (startOfNextWeek.before(endDate) || startOfNextWeek.equals(endDate));
        }

        if (isRecur) {
            if (eventConflict.isEmpty()) {
                for (Assignment taskInList : temp) {
                    events.addTask(taskInList);
                }
            } else {
                return ui.showConflictRecurring(eventConflict);
            }
        } else {
            for (Assignment taskInList : temp) {
                events.removeTask(taskInList);
            }
        }
        storage.updateEventList(events);
        return ui.showRecurring(description, oldStartDateString, endDateString, isBiweekly, isRecur);
    }
}
