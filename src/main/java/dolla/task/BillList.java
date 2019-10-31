package dolla.task;

import dolla.Storage;

import java.util.ArrayList;

//@@author tata

public class BillList extends RecordList{

    public BillList(ArrayList<Record> importBillList) {
        super(importBillList);
    }

    @Override
    public void add(Record newRecord) {
        super.add(newRecord);
        Storage.setBill(get()); //save
    }
}
