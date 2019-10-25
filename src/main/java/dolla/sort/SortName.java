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
     * @param list the ArrayList to be sorted.
     */
    public SortName(ArrayList<Log> list) {
        super(list);
        Collections.sort(list,ListComparator.nameComparator());
        SortUi.printSortedList(list,"name");
    }
}
