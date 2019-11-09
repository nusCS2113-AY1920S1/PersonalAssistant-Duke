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

    public void addWithIndex(int modifyIndex, Record newRecord) {
        list.add(modifyIndex, newRecord);
    }

    public int findExistingRecordIndex(DollaData dollaData, Record record, String mode) {
        return 0;
    }

    public void setRecordList(ArrayList<Record> recordList) {
        this.list = recordList;
    }
}
