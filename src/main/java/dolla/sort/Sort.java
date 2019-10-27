package dolla.sort;

import dolla.task.Record;

import java.util.ArrayList;

class Sort {
    ArrayList<Record> sortedList;

    /**
     * This method will set the ArrayList in this class to the ArrayList
     * being passed in.
     * @param unsortedList the ArrayList to be set to.
     */
    Sort(ArrayList<Record> unsortedList) {
        this.sortedList = unsortedList;
    }
}
