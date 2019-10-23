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
     * Instantiates a new Sort description.
     *
     * @param list the list
     */
    public SortDescription(ArrayList<Log> list) {
        super(list);
        Collections.sort(list,ListComparator.descComparator());
        Ui.printSortedList(list,"description");
    }
}
