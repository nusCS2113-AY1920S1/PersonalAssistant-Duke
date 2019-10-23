package dolla;

import dolla.task.LogList;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class handles Reminder-related methods.
 */
public class Reminder {

    protected LocalDate today;
    private String mode;

    /**
     * Instantiates a new Reminder.
     *
     * @param mode the mode
     */
    public Reminder(String mode) {
        this.mode = "debt";
    }

    /**
     * Show reminder.
     *
     * @param dollaData the dolla data
     */
    public void showReminder(DollaData dollaData) {
        LogList logList = new LogList(new ArrayList<>());
        logList = dollaData.getLogList(mode);
        today = LocalDate.now();
        printReminder(today, logList);
    }

    /**
     * Print reminder.
     *
     * @param today   the today
     * @param logList the log list
     */
    public void printReminder(LocalDate today, LogList logList) {
        System.out.println("\tREMINDER!!!");
        int listNum = 0;
        for (int i = 0; i < logList.size(); i++) {
            LocalDate temp = logList.get().get(i).getDate(); //get the time for that log
            LocalDate check = today.plusDays(2); //remind the user 2 days before
            if (check.compareTo(temp) >= 0) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + logList.get().get(i).getLogText());
            }
        }
        if (listNum == 0) {
            Ui.printNoReminderMsg();
        }
    }
}
