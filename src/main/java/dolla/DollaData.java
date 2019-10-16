package dolla;

import dolla.task.EntryList;
import dolla.task.LogList;

import java.util.ArrayList;

public class DollaData {

    private String mode = "dolla";
    private EntryList entryList;

    public DollaData() {
        this.entryList = new EntryList(new ArrayList<Log>());
        // this.entryList = new EntryList(importEntryList()); TODO: Import from save file
    }

    /**
     * Returns the relevant LogList (ie. EntryList) according to the specified mode.
     * @param mode The mode pertaining to the LogList to be retrieved.
     * @return The LogList according to the specified mode.
     */
    public LogList getLogList(String mode) {
        if (mode.equals("entries")) {
            return entryList;
        }
        return null; // placeholder so that Dolla can compile
    }

    /**
     * Adds a new Log (ie. Entry) into the relevant LogList (ie. EntryList) according to the specified mode.
     * @param mode The mode pertaining to the LogList to be retrieved.
     * @param newLog The new Log to be added into the relevant LogList.
     */
    public void addToLogList(String mode, Log newLog) {
        if (mode.equals("entries")) {
            entryList.add(newLog);
        }
    }

    public String getMode() {
        return mode;
    }

    public void updateMode(String newMode) {
        mode = newMode;
    }
}
