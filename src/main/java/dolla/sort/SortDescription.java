package dolla.sort;

import dolla.ui.SortUi;
import dolla.task.Record;

import java.util.ArrayList;

/**
 * The type Sort description.
 */
public class SortDescription extends Sort {

    //@@author yetong1895
    /**
     * This method will call the descComparator method, sort the input ArrayList according to description
     * and print out the sorted list.
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortDescription(ArrayList<Record> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparatorUtil.descComparator());
        SortUi.printSortedList(sortedList,"description");
    }
}
