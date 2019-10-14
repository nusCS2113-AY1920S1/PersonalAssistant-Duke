package dolla;

import dolla.task.EntryList;

import java.util.ArrayList;

public class DollaData {

    private String mode = "dolla";
    public EntryList entryList; // TODO: Find out alternatives to using a public variable
    Storage saveData = new Storage();

    public DollaData() {
 //       this.entryList = new EntryList(new ArrayList<Log>());
         this.entryList = new EntryList(saveData.getEntries()); //TODO: Import from save file
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
