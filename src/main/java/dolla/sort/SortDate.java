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
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortDate(ArrayList<Log> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparator.dateComparator());
        SortUi.printSortedList(sortedList,"date");
    }
}
