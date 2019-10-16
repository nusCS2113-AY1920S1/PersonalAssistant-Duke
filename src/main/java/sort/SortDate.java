package sort;

import dolla.task.Entry;

import java.util.ArrayList;
import java.util.Collections;

public class SortDate extends Sort{
    ArrayList<Entry> entriesToSort;
//    ArrayList<Limit> limitsToSort;
//    ArrayList<Debt> debtsToSort;

//    public SortDate(ArrayList<Entry> entriesToSort) {
//        this.entriesToSort = entriesToSort;
//        sort();
//        printSortedList();
//    }
    public void sortEntry(ArrayList<Entry> entries) {
        System.out.println("sorting.........");
        this.entriesToSort = entries;
        Collections.sort(entriesToSort,DateComparator.entryDateComparator());

        for (int i = 0; i < entriesToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + entriesToSort.get(i).getLogText());
        }
    }

    /*public void sortLimit(ArrayList<Limit> limits) {
        System.out.println("sorting.........");
        this.limitsToSort = limits;
        Collections.sort(limitsToSort,DateComparator.limitDateComparator());

        for (int i = 0; i < limitsToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + limitsToSort.get(i).getLogText());
        }
    }

    public void sortDebt(ArrayList<Debt> debts) {
        System.out.println("sorting.........");
        this.debtsToSort = debts;
        Collections.sort(debtsToSort,DateComparator.debtDateComparator());

        for (int i = 0; i < debtsToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + debtsToSort.get(i).getLogText());
        }
    }*/



}
