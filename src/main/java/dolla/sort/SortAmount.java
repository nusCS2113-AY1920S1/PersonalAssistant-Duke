package dolla.sort;

import dolla.model.Record;
import dolla.ui.SortUi;

import java.util.ArrayList;

public class SortAmount extends Sort {
    //@@author yetong1895
    /**
    * This method will call the AmountComparator method, sort the input ArrayList according to amount
    * and print out the sorted list.
    * @param unsortedList the ArrayList to be sorted.
    */
    public SortAmount(ArrayList<Record> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparatorUtil.amountComparator());
        SortUi.printSortedList(sortedList,"amount");
    }
}
