package dolla.sort;


import dolla.ui.SortUi;
import dolla.ui.Ui;
import dolla.task.Record;

import java.util.ArrayList;

/**
 * The type Sort date.
 */
public class SortDate extends Sort {

    /**
     * This method will call the dateComparator method, sort the input ArrayList according to date
     * and print out the sorted list.
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortDate(ArrayList<Record> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparatorUtil.dateComparator());
        SortUi.printSortedList(sortedList,"date");
    }
}
