package dolla.task;

import dolla.Storage;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the Limit List.
 */
public class LimitList extends RecordList {

    public LimitList(ArrayList<Record> importLimitList) {
        super(importLimitList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        Storage.setLimits(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setLimits(get()); //save
    }
}
