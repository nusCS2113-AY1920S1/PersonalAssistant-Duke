package dolla.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class RecordList {
    protected ArrayList<Record> list;

    public RecordList(ArrayList<Record> importEntryList) {
        this.list = importEntryList;
        //this.list = new ArrayList<Record>(); // TODO: UPDATE!
    }

    public ArrayList<Record> get() {
        return list;
    }

    public ArrayList<Record> getCloneList() {
        return (ArrayList<Record>) list.clone();
    }

    public void add(Record newRecord) {
        list.add(newRecord);
    }

    public void insertPrevPosition(int prevPosition, Record newRecord) {
        list.add(prevPosition, newRecord);
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
}
