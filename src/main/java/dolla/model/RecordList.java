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

    public void setTypeInRecord(int index, String s) {
        recordArrayList.get(index).setType(s);
    }

    public void setAmountInRecord(int index, double d) {
        recordArrayList.get(index).setAmount(d);
    }

    public void setDescInRecord(int index, String s) {
        recordArrayList.get(index).setDescription(s);
    }

    public void setDateInRecord(int index, LocalDate ld) {
        recordArrayList.get(index).setDate(ld);
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
    protected void addWithIndex(int modifyIndex, Record newRecord) {
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

    /**
     * Method checks to see if the specified record already exists.
     * @param inputRecord The record to be compared against.
     * @return index of the currently existing record (is - 1 if not found)
     */
    public int findExistingRecordIndex(Record inputRecord) {
        for (int i = 0; i < recordArrayList.size(); i += 1) {
            Record cmpRecord = recordArrayList.get(i);
            System.out.println(inputRecord.getRecordDetail());
            System.out.println(cmpRecord.getRecordDetail());
            if (inputRecord.getRecordDetail().equals(cmpRecord.getRecordDetail())) {
                return i;
            }
        }
        return -1;
    }
}
