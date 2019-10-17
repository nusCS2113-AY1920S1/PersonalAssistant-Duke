package dolla.sort;

import dolla.ui.Ui;
import dolla.task.Log;

import java.util.ArrayList;
import java.util.Collections;

public class SortName extends Sort {

    public SortName(ArrayList<Log> list) {
        super(list);
        Collections.sort(list,NameComparator.NameComparator());
        Ui.printSortedList(list,"name");
    }
}
