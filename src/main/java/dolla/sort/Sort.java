package dolla.sort;

import dolla.task.Log;

import java.util.ArrayList;

public class Sort {
    ArrayList<Log> sortedList;

    /**
     * This method will set the ArrayList in this class to the ArrayList
     * being passed in.
     * @param unsortedList the ArrayList to be set to.
     */
    public Sort(ArrayList<Log> unsortedList) {
        this.sortedList = unsortedList;
    }
}
