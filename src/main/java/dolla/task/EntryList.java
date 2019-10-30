package dolla.task;

import dolla.storage.Storage;
import dolla.storage.StorageWrite;

import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends RecordList {
    public EntryList(ArrayList<Record> importEntryList) {
        super(importEntryList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setEntries(get()); //save
    }

    @Override
    public void insertPrevPosition(int prevPosition, Record newRecord) {
        super.insertPrevPosition(prevPosition, newRecord);
        StorageWrite.setEntries(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setEntries(get());
    }
}
