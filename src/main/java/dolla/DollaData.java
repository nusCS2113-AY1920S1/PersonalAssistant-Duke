package dolla;

import dolla.task.*;


import static dolla.Storage.getDebtsFromSave;
import static dolla.Storage.getEntriesFromSave;
import static dolla.Storage.getLimitsFromSave;

public class DollaData {

    private String mode = "dolla";
    private EntryList entryList; // TODO: Find out alternatives to using a public variable
    private DebtList debtList;
    private LimitList limitList;

    private String prevMode;
    private int modifyIndex;

    public DollaData() {
        //this.entryList = new EntryList(new ArrayList<Log>());
        this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
        this.limitList = new LimitList(getLimitsFromSave()); //Import from save file
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
        } else if (mode.equals("limit")) {
            return limitList;
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
        } else if (mode.equals("limit")) {
            limitList.add(newLog);
        }
    }

    public void addToPrevPosition(String mode, Log newLog, int prevPosition) {
        if (mode.equals("entry")) {
            entryList.insertPrevPosition(prevPosition,newLog);
        } else if (mode.equals("debt")) {
            debtList.insertPrevPosition(prevPosition,newLog);
        } else if (mode.equals("limit")) {
            limitList.insertPrevPosition(prevPosition,newLog);
        }
    }

    public void removeFromLogList(String mode, int index) {
        if (mode.equals("entry")) {
            entryList.removeFromList(index);
        } else if (mode.equals("debt")) {
            debtList.removeFromList(index);
        } else if (mode.equals("limit")) {
            limitList.removeFromList(index);
        }
    }

    public void modifyLogList(Log newLog) {
        if (prevMode.equals("entry")) {
            entryList.removeFromList(modifyIndex);
            entryList.addWithIndex(modifyIndex, newLog);
        }
    }

    public String getMode() {
        return mode;
    }

    public void updateMode(String newMode) {
        mode = newMode;
    }

    public void prepForModify(String prevMode, int index) {
        this.prevMode = prevMode;
        modifyIndex = index;
    }

    public int getModifyIndex() {
        return modifyIndex;
    }
}
