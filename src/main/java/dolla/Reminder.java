package dolla;

import dolla.command.Command;
import dolla.task.Log;
import dolla.task.LogList;
import dolla.task.Task;
import dolla.task.TaskList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;



public class Reminder {
    protected LocalDate today;
    private String mode;

    /**
     * Initialise the mode to debt mode
     * @param mode debt
     */
    public Reminder(String mode) {
        this.mode = "debt";
    }

    /**
     * Get the loglist of debt mode and print the reminder
     * @param dollaData
     */
    public void showReminder (DollaData dollaData) {
        LogList logList = new LogList(new ArrayList<>());
        logList = dollaData.getLogList(mode);
        today = LocalDate.now();
        printReminder(today, logList);
    }

    /**
     * Check through the loglist. Print the logs if the due date is less than 2 days from today.
     * @param today today's date
     * @param logList loglist of debt
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
