package dolla;

import dolla.task.EntryList;

import java.util.ArrayList;
import static dolla.Storage.getEntriesFromSave;

public class DollaData {

    private String mode = "dolla";
    public EntryList entryList; // TODO: Find out alternatives to using a public variable

    public DollaData() {
 //       this.entryList = new EntryList(new ArrayList<Log>());
         this.entryList = new EntryList(getEntriesFromSave()); //Import from save file
        System.out.println(entryList.get().size());
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
