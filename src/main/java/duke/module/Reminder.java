package duke.module;

import duke.task.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/**
 * This class serves to get reminders about all the deadlines due before the specified time
 *
 *
 */
public class Reminder {

    protected Date endDate;
    protected Date todayDate;
    protected ArrayList<Item> deadlineList = new ArrayList<>();

    /**
     * Constructor for the Reminder class
     * @param endDate specified deadline in date-time format
     */
    public Reminder(Date endDate) {
        this.endDate = endDate;
        todayDate = new Date();
    }


    public Date getTodayDate() {
        System.out.println("Today's date " + todayDate);
        System.out.println("Saved date: " + endDate);
        return todayDate;
    }
    public Date getEndDate() {
        return endDate;
    }

    public void compareDates (TaskList tasks) {
        ArrayList<Item> tempList = new ArrayList<>();
        try {
            deadlineList.addAll(Objects.requireNonNull(tasks.getReminderList(todayDate, endDate)));
        }
        catch (NullPointerException e) {
            System.out.println("No deadlines due before specified date");
        }
    }

    public void getReminders(TaskList tasks) {
        compareDates(tasks);
        if (!deadlineList.isEmpty()) {
            int count = 1;
            System.out.println("Reminder to do these tasks before " + tasks.dateToStringFormat(endDate));
            for (Item i: deadlineList) {
                System.out.println(count++ +"."+ i.toString());
            }
        }
    }
}
