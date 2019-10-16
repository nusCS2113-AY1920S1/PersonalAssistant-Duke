package dolla.task;

import dolla.Log;
import dolla.Storage;
import sort.Sort;

import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends LogList{
    protected ArrayList<Entry> entryList;
    public EntryList(ArrayList<Entry> importEntryList) {
        this.entryList = importEntryList;
//        super(importEntryList);
    }

    public ArrayList<Entry> get() {
        return entryList;
    }

    public void add(Entry newEntry) {
        entryList.add(newEntry);
        Storage.setEntries(get()); //save
    }

    public Entry getFromList(int index) {
        return entryList.get(index);
    }

    @Override
    public int size() {
        return entryList.size();
    }

    @Override
    public void removeFromList(int index) {
        entryList.remove(index);
    }

}
