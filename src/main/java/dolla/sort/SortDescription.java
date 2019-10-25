package dolla.sort;

import dolla.Ui;
import dolla.task.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Sort description.
 */
public class SortDescription extends Sort {

    /**
     * This method will call the descComparator method, sort the input ArrayList according to description
     * and print out the sorted list.
     * @param list the ArrayList to be sorted.
     */
    public SortDescription(ArrayList<Log> list) {
        super(list);
        Collections.sort(list,ListComparator.descComparator());
        Ui.printSortedList(list,"description");
    }
}
