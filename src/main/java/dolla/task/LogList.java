package dolla.task;

import dolla.Log;

import java.util.ArrayList;

public class LogList {
    protected ArrayList<Log> list;

    public LogList(ArrayList<Log> importEntryList) {
        this.list = importEntryList;
        //this.list = new ArrayList<Log>(); // TODO: UPDATE!
    }

    public ArrayList<Log> get() {
        return list;
    }

    public void add(Log newLog) {
        list.add(newLog);
    }

    public int size() {
        return list.size();
    }

    public Log getFromList(int index) {
        return list.get(index);
    }

    public void removeFromList(int index) {
        list.remove(index);
    }

    public void addWithIndex(int modifyIndex, Log newLog) {
        list.add(modifyIndex, newLog);
    }
}
