package dolla.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecordList {
    protected ArrayList<Record> recordArrayList;

    public RecordList(ArrayList<Record> importEntryList) {
        this.recordArrayList = importEntryList;
    }

    public ArrayList<Record> get() {
        return recordArrayList;
    }

    public ArrayList<Record> getCloneList() {
        return new ArrayList<>(recordArrayList);
    }

    public Record getFromList(int index) {
        return recordArrayList.get(index);
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.recordArrayList = recordList;
    }

    public void add(Record newRecord) {
        recordArrayList.add(newRecord);
    }

    /**
     * Inserts a new record at the specified modifyIndex.
     * @param modifyIndex Index where the record should be added into the recordList.
     * @param newRecord The record to be added.
     */
    public void addWithIndex(int modifyIndex, Record newRecord) {
        recordArrayList.add(modifyIndex, newRecord);
    }

    public int size() {
        return recordArrayList.size();
    }

    public void removeFromList(int index) {
        recordArrayList.remove(index);
    }

    public boolean isEmpty() {
        return recordArrayList.isEmpty();
    }

    public static Boolean recordDoesNotExist(int recordIndex) {
        return (recordIndex == - 1);
    }
}
