package dolla.sort;

import dolla.task.Log;
import dolla.ui.SortUi;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Sort name.
 */
public class SortName extends Sort {

    /**
     * This method will call the nameComparator method, sort the input ArrayList according to name
     * and print out the sorted list.
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortName(ArrayList<Log> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparator.nameComparator());
        SortUi.printSortedList(sortedList,"name");
    }
}
