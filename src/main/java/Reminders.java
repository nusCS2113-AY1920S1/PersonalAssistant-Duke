import storage.Storage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Reminders {

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
                    //delete reminder from list
                    storage.deleteItemFromFile(convertToData(reminderInfo), "/home/tessa/Documents/CS2113/main/src/main/data/reminders.txt");
                }
            }
        }
    }

    public String convertToData(String[] reminderInfo) {
        return reminderInfo[0].trim() + " | " + reminderInfo[1].trim() + " | " + reminderInfo[2].trim();
    }

    public void getRemindersList(Storage storage) {
        reminderList = storage.loadReminderFile("src/main/data/reminders.txt");
    }

    public void printReminder(String[] reminderInfo) {
        System.out.println("     You have tasks due!\n     " + reminderInfo[0] + "by " + reminderInfo[1]);
        System.out.println("    ____________________________________________________________");
    }

    public void getUpcomingReminders() {
        for (String[] x: reminderList) {
            System.out.println(x[0]);
        }
    }

}
