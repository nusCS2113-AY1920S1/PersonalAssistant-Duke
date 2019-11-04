package dolla.task;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

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
