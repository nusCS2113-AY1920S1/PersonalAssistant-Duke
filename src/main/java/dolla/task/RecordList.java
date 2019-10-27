package dolla.task;

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

    public void removeFromList(int index) {
        list.remove(index);
    }

    public void addWithIndex(int modifyIndex, Record newRecord) {
        list.add(modifyIndex, newRecord);
    }
}
