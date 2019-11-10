package dolla.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecordList {
    protected ArrayList<Record> list;

    public RecordList(ArrayList<Record> importEntryList) {
        this.list = importEntryList;
    }

    public ArrayList<Record> get() {
        return list;
    }

    public ArrayList<Record> getCloneList() {
        return new ArrayList<>(list);
    }

    public void add(Record newRecord) {
        list.add(newRecord);
    }

    public int size() {
        return list.size();
    }

    public Record getFromList(int index) {
        return list.get(index);
    }

    public void setTypeInRecord(int index, String s) {
        list.get(index).setType(s);
    }

    public void setAmountInRecord(int index, double d) {
        list.get(index).setAmount(d);
    }

    public void setDescInRecord(int index, String s) {
        list.get(index).setDescription(s);
    }

    public void setDateInRecord(int index, LocalDate ld) {
        list.get(index).setDate(ld);
    }

    public void removeFromList(int index) {
        list.remove(index);
    }

    protected void addWithIndex(int modifyIndex, Record newRecord) {
        list.add(modifyIndex, newRecord);
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.list = recordList;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Method checks to see if the specified record already exists.
     * @param inputRecord The record to be compared against.
     * @return index of the currently existing record (is - 1 if not found)
     */
    public int findExistingRecordIndex(Record inputRecord) {
        for (int i = 0; i < list.size(); i += 1) {
            Record cmpRecord = list.get(i);
            System.out.println(inputRecord.getRecordDetail());
            System.out.println(cmpRecord.getRecordDetail());
            if (inputRecord.getRecordDetail().equals(cmpRecord.getRecordDetail())) {
                return i;
            }
        }
        return -1;
    }
}
