package dolla.sort;

import dolla.Ui;
import dolla.task.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Sort date.
 */
public class SortDate extends Sort {

    /**
     * Instantiates a new Sort date.
     *
     * @param list the list
     */
    public SortDate(ArrayList<Log> list) {
        super(list);
        Collections.sort(list, ListComparator.dateComparator());
        Ui.printSortedList(list,"date");
    }
}
