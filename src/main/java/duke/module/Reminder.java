package duke.module;


import duke.task.Item;
import duke.task.TaskList;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/**
 * This class serves to get reminders about all the deadlines
 * due before the specified time.
 *
 *
 */
public class Reminder {

    /**
     * Represents the end date of the reminder.
     */
    private Date endDate;

    /**
     * Represents the current date of the reminder.
     */
    private Date todayDate;

    /**
     * Represents the list of deadline dates for the reminder.
     */
    private ArrayList<Item> deadlineList = new ArrayList<>();

    /**
     * Constructor for the Reminder class.
     * @param myEndDate specified deadline in date-time format
     */
    public Reminder(final Date myEndDate) {
        this.endDate = myEndDate;
        todayDate = new Date();
    }

    /**
     * Getter function to get the current date.
     * @return A string containing the current date.
     */
    public final Date getTodayDate() {
        System.out.println("Today's date " + todayDate);
        System.out.println("Saved date: " + endDate);
        return todayDate;
    }

    /**
     * Getter function to get the end date.
     * @return A string containing the end date.
     */
    public final Date getEndDate() {
        return endDate;
    }

    /**
     * A function to compare the dates to check if there are any deadlines
     * due before specified date.
     * @param tasks The object required to use the dateToStringFormat method.
     */
    public final void compareDates(final TaskList tasks) {
        ArrayList<Item> tempList = new ArrayList<>();
        try {
            deadlineList.addAll(Objects.requireNonNull(
                tasks.getReminderList(todayDate, endDate)));
        } catch (NullPointerException e) {
            System.out.println("No deadlines due before specified date");
        }
    }

    /**
     * A getter function to obtain reminders.
     * @param tasks The object required to use the dateToStringFormat method.
     */
    public final void getReminders(final TaskList tasks) {
        compareDates(tasks);
        if (!deadlineList.isEmpty()) {
            int count = 1;
            System.out.println("Reminder to do these tasks before "
                + tasks.dateToStringFormat(endDate));
            for (Item i: deadlineList) {
                System.out.println(count++ + "." + i.toString());
            }
        }
    }
}
