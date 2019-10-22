package dolla.task;

import dolla.Storage;

import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends LogList {
    public EntryList(ArrayList<Log> importEntryList) {
        super(importEntryList);
    }

    @Override
    public void add(Log newLog) {
        super.add(newLog);
        Storage.setEntries(get()); //save
    }

    @Override
    public void insertPrevPosition(int prevPosition, Log newLog) {
        super.insertPrevPosition(prevPosition, newLog);
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setEntries(get());
    }
}
