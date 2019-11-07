package dolla.model;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

//@@author yetong1895
/**
 * Holds all the shortcut that have been added to Dolla.
 */
public class ShortcutList extends RecordList {
    public ShortcutList(ArrayList<Record> importEntryList) {
        super(importEntryList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setShortcuts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setShortcuts(get()); //save
    }
}
