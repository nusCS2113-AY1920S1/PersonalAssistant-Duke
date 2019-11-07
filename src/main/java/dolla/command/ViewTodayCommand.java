package dolla.command;

import dolla.model.DollaData;
import dolla.model.EntryList;
import dolla.model.Record;
import dolla.model.RecordList;
import dolla.ui.Ui;
import dolla.ui.ViewUi;

import java.time.LocalDate;

public class ViewTodayCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        LocalDate today = LocalDate.now();
        //double todayOverview = dollaData.dateOverallExpense(today);
        //Ui.printSingleOverview(todayOverview);

        double sum = 0;
        RecordList entryList = dollaData.getRecordListObj(MODE_ENTRY);

        for (int i = 0; i < entryList.size(); i += 1) {
            Record currEntry = entryList.getFromList(i);
            LocalDate currDate = currEntry.getDate();

            if (isSameDate(currDate, today)) {
                String currType = currEntry.getType();
                double currAmount = currEntry.getAmount();
                if (currType.equals("expense")) {
                    currAmount *= -1;
                }
                String currDesc = currEntry.getDescription();
                ViewUi.printViewSingleExpense(currEntry);
                sum += currAmount;
            }


        }

    }

    @Override
    public String getCommandInfo() {
        return null;
    }

    private boolean isSameDate(LocalDate d1, LocalDate d2) {
        if (d1.compareTo(d2) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
