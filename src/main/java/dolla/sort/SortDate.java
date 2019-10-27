package dolla.sort;

import dolla.ui.Ui;
import dolla.task.Record;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Sort date.
 */
public class SortDate extends Sort {

    /**
     * This method will call the dateComparator method, sort the input ArrayList according to date
     * and print out the sorted list.
     * @param list the ArrayList to be sorted.
     */
    public SortDate(ArrayList<Record> list) {
        super(list);
        Collections.sort(list, ListComparator.dateComparator());
        Ui.printSortedList(list,"date");
    }
}
