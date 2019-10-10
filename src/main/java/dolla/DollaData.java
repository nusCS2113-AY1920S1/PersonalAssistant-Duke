package dolla;

import dolla.task.EntryList;

import java.util.ArrayList;

public class DollaData {

    private String mode = "dolla";
    private EntryList entryList;

    public DollaData() {
        this.entryList = new EntryList(new ArrayList<Log>());
        // this.entryList = new EntryList(importEntryList()); TODO: Import from save file
    }

    public EntryList getEntryList() {
        return entryList;
    }

    public String getMode() {
        return mode;
    }

    public void updateMode(String newMode) {
        mode = newMode;
    }
}
