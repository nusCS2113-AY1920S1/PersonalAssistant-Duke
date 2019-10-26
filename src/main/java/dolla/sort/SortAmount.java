package dolla.sort;

import dolla.task.Log;
import dolla.ui.SortUi;

import java.util.ArrayList;

public class SortAmount extends Sort{
    public SortAmount(ArrayList<Log> unsortedList) {
        super(unsortedList);
        sortedList.sort(ListComparator.amountComparator());
        SortUi.printSortedList(sortedList,"amount");
    }
}
