import storage.Storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Reminders {
    public static void getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        System.out.println(java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")));
    }

    protected ArrayList<String[]> reminderList;

    public void checkForReminders(Storage storage) {
        //update clock
        String currentDate = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String currentTime = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
        String currentDateTime = currentDate + " " + currentTime;
//        System.out.println(currentDateTime);

        getRemindersList(storage);
        //check list of reminders
        for (String[] reminderInfo : reminderList) {
            if (reminderInfo.length == 3) {
                if (reminderInfo[2].equals(currentDateTime)) {
                    //time to remind
                    printReminder(reminderInfo);
                }
            }
        }
    }

    public void getRemindersList(Storage storage) {
        reminderList = storage.loadReminderFile("/home/tessa/Documents/CS2113/main/data/reminders.txt");
    }

    public void printReminder(String[] reminderInfo) {
        System.out.println("You have tasks due!\n" + reminderInfo[0] + " by " + reminderInfo[1]);
    }
}
