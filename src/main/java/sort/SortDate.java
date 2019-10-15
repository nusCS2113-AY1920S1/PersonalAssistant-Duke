package sort;

import dolla.task.Entry;

import java.util.ArrayList;
import java.util.Collections;

public class SortDate extends Sort{
    ArrayList<Entry> sortedEntriesList;

    SortDate(ArrayList<Entry> entriesToSort) {
        super(entriesToSort);
        sortedEntriesList = sort();
    }

    @Override
    public void printSortedList() {
        for(Entry entry : sortedEntriesList) {
            //print
        }
    }

    public ArrayList<Entry> sort() {
        Collections.sort(entriesToSort);
        return entriesToSort;
    }

}
