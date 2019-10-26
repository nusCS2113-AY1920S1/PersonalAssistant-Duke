package dolla.sort;

import dolla.task.Log;
import dolla.ui.SortUi;

import java.util.ArrayList;

public class SortAmount extends Sort {

    /**
     * This method will call the AmountComparator method, sort the input ArrayList according to amount
     * and print out the sorted list.
     * @param unsortedList the ArrayList to be sorted.
     */
    public SortAmount(ArrayList<Log> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparator.amountComparator());
        SortUi.printSortedList(sortedList,"amount");
    }
}
