package dolla.sort;

import dolla.task.Log;
import dolla.ui.SortUi;

import java.util.ArrayList;
import java.util.Collections;

public class SortAmount extends Sort{
    public SortAmount(ArrayList<Log> list) {
        super(list);
        Collections.sort(list, ListComparator.amountComparator());
        SortUi.printSortedList(list,"amout");
    }
}
