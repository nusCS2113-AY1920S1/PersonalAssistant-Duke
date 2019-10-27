package dolla.sort;

import dolla.ui.Ui;
import dolla.task.Record;

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
    public SortName(ArrayList<Record> list) {
        super(list);
        Collections.sort(list,ListComparator.nameComparator());
        Ui.printSortedList(list,"name");
    }
}
