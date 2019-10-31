package dolla.task;

import dolla.Storage;

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
        Storage.setDebts(get()); //save
    }

    @Override
    public void insertPrevPosition(int prevPosition, Record newRecord) {
        super.insertPrevPosition(prevPosition, newRecord);
        Storage.setDebts(get()); //save
    }

    @Override
    public void removeFromList(int index) {
        super.removeFromList(index);
        Storage.setDebts(get()); //save
    }

    @Override
    public void addWithIndex(int modifyIndex, Record newRecord) {
        super.addWithIndex(modifyIndex, newRecord);
        Storage.setDebts(get());
    }
}
