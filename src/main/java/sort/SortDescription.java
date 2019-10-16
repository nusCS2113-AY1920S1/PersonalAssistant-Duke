package sort;

import dolla.task.Entry;

import java.util.ArrayList;
import java.util.Collections;

public class SortDescription extends Sort {
    ArrayList<Entry> entriesToSort;

    public void setEntriesToSort(ArrayList<Entry> entriesToSort) {
        this.entriesToSort = entriesToSort;
        sort();
        printSortedList();
    }

    @Override
    public void printSortedList() {
        for (int i = 0; i < entriesToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + entriesToSort.get(i).getLogText());
        }
    }

    public ArrayList<Entry> sort() {
        System.out.println("here");
        Collections.sort(entriesToSort,DescriptionComparator.entryDescComparator());
        return entriesToSort;
    }
}
