package Module;
import Task.TaskList;
import Task.item;

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
    protected ArrayList<item> deadlineList = new ArrayList<>();

    /**
     * Constructor for the Reminder class
     * @param endDate specified deadline in date-time format
     */
    public Reminder (Date endDate) {
        this.endDate = endDate;
        todayDate = new Date();
    }



    public Date getTodayDate () {
        System.out.println("Today's date " + todayDate);
        System.out.println("Saved date: " + endDate);
        return todayDate;
    }
    public Date getEndDate () {
        return endDate;
    }

    public void compareDates () {
        ArrayList<item> tempList = new ArrayList<>();
        try {
            deadlineList.addAll(Objects.requireNonNull(TaskList.getReminderList(todayDate, endDate)));
        }
        catch (NullPointerException e) {
            System.out.println("No deadlines due before specified date");
        }
    }

    public void getReminders () {
        compareDates();
        if (!deadlineList.isEmpty()) {
            int count = 1;
            System.out.println("Reminder to do these tasks before " + TaskList.dateToStringFormat(endDate));
            for (item i: deadlineList) {
                System.out.println(count++ +"."+ i.toString());
            }
        }
    }
}
