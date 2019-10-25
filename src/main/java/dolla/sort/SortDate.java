package dolla.sort;

import dolla.task.Log;
import dolla.ui.SortUi;

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
    public SortDate(ArrayList<Log> list) {
        super(list);
        Collections.sort(list, ListComparator.dateComparator());
        SortUi.printSortedList(list,"date");
    }
}
