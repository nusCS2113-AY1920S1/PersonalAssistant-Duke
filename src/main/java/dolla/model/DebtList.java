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
        this.list = recordList;
        StorageWrite.setDebts(get());
    }


    @Override
    protected void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        StorageWrite.setDebts(get());
    }
}
