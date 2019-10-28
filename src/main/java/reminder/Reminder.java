package reminder;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reminder {
    Timer timer;

    public Reminder(Date date) {
        timer = new Timer();
        timer.schedule(new RemindTask(timer), date);
    }

    public static Date parseDate(String dateInput) {
        String pattern = "dd-MM-yyyy HHmm";
        SimpleDateFormat formattedDate = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            date = formattedDate.parse(dateInput);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
