package dolla.command.view;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.RecordList;
import dolla.ui.Ui;
import dolla.ui.ViewUi;

import java.time.LocalDate;

public class ViewCommand extends Command {

    LocalDate cmpDate;
    String dateStr;

    @Override
    public void execute(DollaData dollaData) {

        cmpDate = LocalDate.now(); // to change

        double sum = 0;
        RecordList entryList = dollaData.getRecordListObj(MODE_ENTRY);
        boolean hasRelevantEntries = false;

        if (entryList.isEmpty()) {
            Ui.printEmptyListError(MODE_ENTRY);
            return;
        }

        for (int i = 0; i < entryList.size(); i += 1) {
            Record currEntry = entryList.getFromList(i);
            LocalDate currDate = currEntry.getDate();

            if (!isSameDate(currDate, cmpDate)) {
                continue;
            }

            hasRelevantEntries = true;

            double currAmount = getSignedAmount(currEntry);
            String currDesc = currEntry.getDescription();

            ViewUi.printViewSingleExpense(currAmount, currDesc);
            sum += currAmount;

        }

        if (hasRelevantEntries) {
            ViewUi.printOverallExpense(sum, dateStr);
        } else {
            ViewUi.printNoRelevantExpense(dateStr);
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

    private double getSignedAmount(Record currEntry) {
        String type = currEntry.getType();
        double amount = currEntry.getAmount();
        if (type.equals("expense")) {
            amount *= -1;
        }
        return amount;
    }

}