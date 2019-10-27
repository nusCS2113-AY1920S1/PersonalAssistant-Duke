package dolla.sort;

import dolla.ui.SortUi;
import dolla.ui.Ui;
import dolla.task.Record;

import java.util.ArrayList;

/**
 * The type Sort name.
 */
public class SortName extends Sort {

    /**
     * This method will call the nameComparator method, sort the input ArrayList according to name
     * and print out the sorted list.
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortName(ArrayList<Record> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparatorUtil.nameComparator());
        SortUi.printSortedList(sortedList,"name");
    }
}
