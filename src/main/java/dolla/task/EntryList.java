package dolla.task;

import dolla.Storage;

import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends LogList{
    protected ArrayList<Entry> entryList;
    public EntryList(ArrayList<Log> importEntryList) {
        super(importEntryList);
//        super(importEntryList);
    }

    @Override
    public void add(Log newLog) {
        super.add(newLog);
        Storage.setEntries(get()); //save
    }
}
