package dolla;

import dolla.task.*;
import dolla.task.Record;

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

    /**
     * Instantiates a new Dolla data.
     */
    public DollaData() {
        //this.entryList = new EntryList(new ArrayList<Record>());
        this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
        this.limitList = new LimitList(getLimitsFromSave()); //Import from save file
        this.debtList = new DebtList(getDebtsFromSave()); //Import from save file
    }

    /**
     * Returns the relevant RecordList (ie. EntryList) according to the specified mode.
     *
     * @param mode The mode pertaining to the RecordList to be retrieved.
     * @return The RecordList according to the specified mode.
     */
    public RecordList getLogList(String mode) {
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
     * Adds a new Record (ie. Entry) into the relevant RecordList (ie. EntryList) according to the specified mode.
     *
     * @param mode   The mode pertaining to the RecordList to be retrieved.
     * @param newRecord The new Record to be added into the relevant RecordList.
     */
    public void addToLogList(String mode, Record newRecord) {
        if (mode.equals("entry")) {
            entryList.add(newRecord);
        } else if (mode.equals("debt")) {
            debtList.add(newRecord);
        } else if (mode.equals("limit")) {
            limitList.add(newRecord);
        }
    }

    /**
     * Add to prev position.
     *
     * @param mode         the mode
     * @param newRecord       the new log
     * @param prevPosition the prev position
     */
    public void addToPrevPosition(String mode, Record newRecord, int prevPosition) {
        if (mode.equals("entry")) {
            entryList.insertPrevPosition(prevPosition, newRecord);
        } else if (mode.equals("debt")) {
            debtList.insertPrevPosition(prevPosition, newRecord);
        } else if (mode.equals("limit")) {
            limitList.insertPrevPosition(prevPosition, newRecord);
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
     * Modify log list.
     *
     * @param newRecord the new log
     */
    public void modifyLogList(Record newRecord) {
        if (prevMode.equals("entry")) {
            entryList.removeFromList(modifyIndex);
            entryList.addWithIndex(modifyIndex, newRecord);
        }
    }

    /**
     * Gets mode.
     *
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Update mode.
     *
     * @param newMode the new mode
     */
    public void updateMode(String newMode) {
        mode = newMode;
    }

    /**
     * Prep for modify.
     *
     * @param prevMode the prev mode
     * @param index    the index
     */
    public void prepForModify(String prevMode, int index) {
        this.prevMode = prevMode;
        modifyIndex = index;
    }

    /**
     * Gets modify index.
     *
     * @return the modify index
     */
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
