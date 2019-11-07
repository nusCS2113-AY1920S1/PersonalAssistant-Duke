package dolla;

import dolla.model.DollaData;
import dolla.model.RecordList;
import dolla.ui.Ui;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author tatayu
/**
 * Class handles Reminder-related methods.
 */
public class Reminder implements ModeStringList {

    protected LocalDate today;
    private String mode;

    /**
     * Instantiates a new Reminder.
     * @param mode the mode
     */
    public Reminder(String mode) {
        this.mode = MODE_DEBT;
    }

    /**
     * Show reminder.
     * @param dollaData the dolla data
     */
    public void showReminder(DollaData dollaData) {
        RecordList recordList = new RecordList(new ArrayList<>());
        recordList = dollaData.getRecordListObj(mode);
        today = LocalDate.now();
        printReminder(today, recordList);
    }

    /**
     * Print reminder.
     * @param today   Today's date.
     * @param recordList The list of logs of debt.
     */
    public void printReminder(LocalDate today, RecordList recordList) {
        System.out.println("\tREMINDER!!!");
        int listNum = 0;
        for (int i = 0; i < recordList.size(); i++) {
            LocalDate temp = recordList.get().get(i).getDate(); //get the time for that log
            LocalDate check = today.plusDays(2); //remind the user 2 days before
            if (check.compareTo(temp) >= 0 && temp.compareTo(today) > 0) {
                listNum += 1;
                System.out.println("\t" + listNum + ". " + recordList.get().get(i).getRecordDetail());
            }
        }
        if (listNum == 0) {
            Ui.printNoReminderMsg();
        }
    }
}
