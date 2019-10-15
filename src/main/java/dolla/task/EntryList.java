package dolla.task;

import dolla.Log;
import sort.Sort;

import java.util.ArrayList;

/**
 * Holds all the entries that have been added to Dolla.
 */
public class EntryList extends LogList {

    Sort sort;
    public EntryList(ArrayList<Log> importEntryList) {
        super(importEntryList);
//        sort = new Sort(importEntryList);
    }

}
