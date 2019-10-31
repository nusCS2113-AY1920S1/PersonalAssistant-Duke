package dolla.task;

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
    public void insertPrevPosition(int prevPosition, Record newRecord) {
        super.insertPrevPosition(prevPosition, newRecord);
        StorageWrite.setDebts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        StorageWrite.setDebts(get()); //save
    }
}
