package dolla.model;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

//@@author tatayu
/**
 * Holds all the debts that have been added to Dolla.
 */
public class DebtList extends RecordList {

    public DebtList(ArrayList<Record> importDebtList) {
        super(importDebtList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setDebts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setDebts(get()); //save
    }

    @Override
    public void setRecordList(ArrayList<Record> recordList) {
        this.recordArrayList = recordList;
        StorageWrite.setDebts(get());
    }


    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setDebts(get());
    }

    /**
     * Method checks to see if the specified debt already exists.
     * @param inputRecord The record to be compared against.
     * @return index of the currently existing record (is - 1 if not found)
     */
    public int findExistingDebtIndex(Record inputRecord) {
        for (int i = 0; i < recordArrayList.size(); i += 1) {
            Record cmpRecord = recordArrayList.get(i);
            //System.out.println(inputRecord.getRecordDetail());
            //System.out.println(cmpRecord.getRecordDetail());
            if (inputRecord.getRecordDetail().equals(cmpRecord.getRecordDetail())) {
                return i;
            }
        }
        return -1;
    }
}
