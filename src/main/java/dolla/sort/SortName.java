package dolla.sort;

import dolla.Ui;
import dolla.task.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Sort name.
 */
public class SortName extends Sort {

    /**
     * Instantiates a new Sort name.
     *
     * @param list the list
     */
    public SortName(ArrayList<Log> list) {
        super(list);
        Collections.sort(list,ListComparator.nameComparator());
        Ui.printSortedList(list,"name");
    }
}
