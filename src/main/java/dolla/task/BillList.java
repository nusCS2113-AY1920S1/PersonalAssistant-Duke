package dolla.task;

import dolla.storage.StorageWrite;

import java.util.ArrayList;

//@@author tata

public class BillList extends RecordList{

    public BillList(ArrayList<Record> importBillList) {
        super(importBillList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        StorageWrite.setBill(get()); //save
    }
}
