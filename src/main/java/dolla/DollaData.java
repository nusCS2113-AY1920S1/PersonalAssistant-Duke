package dolla;

import dolla.task.DebtList;
import dolla.task.EntryList;
import dolla.task.LimitList;
import dolla.task.LogList;
import dolla.task.Log;

import static dolla.Storage.getDebtsFromSave;
import static dolla.Storage.getEntriesFromSave;
import static dolla.Storage.getLimitsFromSave;

/**
 * The type Dolla data.
 */
public class DollaData {

    private String mode = "dolla";
    private EntryList entryList; // TODO: Find out alternatives to using a public variable
    private DebtList debtList;
    private LimitList limitList;

    private String prevMode;
    private int modifyIndex;

    /**
     * Creates an instance of DollaDota to store and manipulate data.
     */
    public DollaData() {
        //this.entryList = new EntryList(new ArrayList<Log>());
        this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
        this.limitList = new LimitList(getLimitsFromSave()); //Import from save file
        this.debtList = new DebtList(getDebtsFromSave()); //Import from save file
    }

    /**
     * Returns the relevant LogList (ie. EntryList) according to the specified mode.
     *
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
     *
     * @param mode   The mode pertaining to the LogList to be retrieved.
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

    /**
     * Add to prev position.
     *
     * @param mode         the mode
     * @param newLog       the new log
     * @param prevPosition the prev position
     */
    public void addToPrevPosition(String mode, Log newLog, int prevPosition) {
        if (mode.equals("entry")) {
            entryList.insertPrevPosition(prevPosition,newLog);
        } else if (mode.equals("debt")) {
            debtList.insertPrevPosition(prevPosition,newLog);
        } else if (mode.equals("limit")) {
            limitList.insertPrevPosition(prevPosition,newLog);
        }
    }

    /**
     * Remove from log list.
     *
     * @param mode  the mode
     * @param index the index
     */
    public void removeFromLogList(String mode, int index) {
        if (mode.equals("entry")) {
            entryList.removeFromList(index);
        } else if (mode.equals("debt")) {
            debtList.removeFromList(index);
        } else if (mode.equals("limit")) {
            limitList.removeFromList(index);
        }
    }

    /**
     * Changes the content of a particular log in list to the specified one.
     * @param newLog The new log to replace the current item on the list.
     */
    public void modifyLogList(Log newLog) {
        if (prevMode.equals("entry")) {
            entryList.removeFromList(modifyIndex);
            entryList.addWithIndex(modifyIndex, newLog);
        }
    }

    public String getMode() {
        return mode;
    }

    /**
     * Updates Dolla's mode to the specified new mode.
     * @param newMode the mode to update to.
     */
    public void updateMode(String newMode) {
        mode = newMode;
    }

    /**
     * Prepares Dolla for modify mode by storing the previous mode and the index of the log to be modified.
     * @param prevMode the current mode of Dolla when this method is called.
     * @param index    the index of the log to be modified.
     */
    public void prepForModify(String prevMode, int index) {
        this.prevMode = prevMode;
        modifyIndex = index;
    }

    public int getModifyIndex() {
        return modifyIndex;
    }

    /**
     * Remove limit.
     *
     * @param type     the type of limit to remove
     * @param duration the duration of the limit to remove
     */
    public void removeLimit(String type, String duration) {
        //remove limit from list
    }

}
