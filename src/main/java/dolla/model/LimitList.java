package dolla.model;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the LimitList (add, remove, check for existing limits).
 */
//@@author Weng-Kexin
public class LimitList extends RecordList {

    public LimitList(ArrayList<Record> importLimitList) {
        super(importLimitList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setLimits(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setLimits(get()); //save
    }

    @Override
    public void setRecordList(ArrayList<Record> recordList) {
        this.list = recordList;
        StorageWrite.setLimits(get());
    }

    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setLimits(get());
    }

    /**
     * Method checks to see if the input limit already exists.
     * @param dollaData The storage container for all the lists.
     * @param inputRecord The limit being input by user.
     * @param mode The mode the user is on.
     * @return index of the currently existing limit (is - 1 if not found)
     */
    @Override
    public int findExistingRecordIndex(DollaData dollaData, Record inputRecord, String mode) {
        Limit inputLimit = (Limit) inputRecord;
        int index = - 1;
        LimitList limitList = (LimitList) dollaData.getRecordListObj(mode);
        Limit currLimit;
        String currType;
        String currDuration;
        for (int i = 0; i < limitList.size(); i++) {
            currLimit = (Limit) limitList.getFromList(i);
            currType = currLimit.type;
            currDuration = currLimit.duration;
            if (currType.equals(inputLimit.type) && currDuration.equals(inputLimit.duration)) {
                index = i;
                break;
            }
        }
        return index;
    }
}

