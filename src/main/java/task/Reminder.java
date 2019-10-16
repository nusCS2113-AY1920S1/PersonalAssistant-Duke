package task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Reminder Class to handle reminders.
 */
public class Reminder {
    String currDate;
    Calendar cal;

    Reminder() {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

    }

    /**
     * Gets the current date.
     */
    public void getCurrDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        currDate = formatter.format(date);
    }


}
