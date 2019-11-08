package dolla.command.view;

import dolla.command.Command;
import dolla.model.DollaData;
import dolla.model.Record;
import dolla.model.RecordList;
import dolla.ui.Ui;
import dolla.ui.ViewUi;

import java.time.LocalDate;

public class ViewCommand extends Command {

    protected LocalDate cmpDate;
    protected String dateStr;
    protected final String STR_TODAY = "today";
    protected final String STR_EXPENSE = "expense";


    @Override
    public void execute(DollaData dollaData) {

        double sum = 0;
        RecordList entryList = dollaData.getRecordListObj(MODE_ENTRY);
        boolean hasRelevantEntries = false;

        if (entryList.isEmpty()) {
            Ui.printEmptyListError(MODE_ENTRY);
            return;
        }

        ViewUi.printStartLine();
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
        if (type.equals(STR_EXPENSE)) {
            amount *= -1;
        }
        return amount;
    }

}