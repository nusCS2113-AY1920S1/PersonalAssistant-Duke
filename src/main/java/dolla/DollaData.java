package dolla;

import dolla.task.Entry;
import dolla.task.DebtList;
import dolla.task.EntryList;
import dolla.task.Log;
import dolla.task.LogList;

import java.util.ArrayList;

import static dolla.Storage.getDebtsFromSave;
import static dolla.Storage.getEntriesFromSave;

public class DollaData {

    private String mode = "dolla";
    private EntryList entryList; // TODO: Find out alternatives to using a public variable
    private DebtList debtList;

    public DollaData() {
 //       this.entryList = new EntryList(new ArrayList<Log>());
         this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
//        this.limitList = new LimitList(getLimitsFromSave()); //Import from save file
        this.debtList = new DebtList(getDebtsFromSave()); //Import from save file
    }

    /**
     * Returns the relevant LogList (ie. EntryList) according to the specified mode.
     * @param mode The mode pertaining to the LogList to be retrieved.
     * @return The LogList according to the specified mode.
     */
    public LogList getLogList(String mode) {
        if (mode.equals("entry")) {
            return entryList;
        } else if (mode.equals("debt")) {
            return debtList;
        }
        return null; // placeholder so that Dolla can compile
    }

    /**
     * Adds a new Log (ie. Entry) into the relevant LogList (ie. EntryList) according to the specified mode.
     * @param mode The mode pertaining to the LogList to be retrieved.
     * @param newLog The new Log to be added into the relevant LogList.
     */
    public void addToLogList(String mode, Log newLog) {
            if (mode.equals("entry")) {
                entryList.add(newLog);
            } else if (mode.equals("debt")) {
                debtList.add(newLog);
            }
    }

    //independent add
//    public void addToEntryList(Entry newEntry) {
//            entryList.add(newEntry);
//    }
/*
    //independent add
    public void addToDebtList(Entry newDebt) {
        debtList.add(newDebt);
    }

    //independent add
    public void addToEntryList(Entry newLimit) {
        limitList.add(newLimit);
    }
*/
    public String getMode() {
        return mode;
    }

    public void updateMode(String newMode) {
        mode = newMode;
    }
}
