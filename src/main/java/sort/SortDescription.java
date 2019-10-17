package sort;

import dolla.task.Entry;
import dolla.task.Log;

import java.util.ArrayList;
import java.util.Collections;

public class SortDescription extends Sort {
    ArrayList<Log> entriesToSort;
//    ArrayList<Limit> limitsToSort;
//    ArrayList<Debt> debtsToSort;

    public void sortEntry(ArrayList<Log> entries) {
        System.out.println("sorting desc");
        this.entriesToSort = entries;
        Collections.sort(entriesToSort,DescriptionComparator.entryDescComparator());

        for (int i = 0; i < entriesToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + entriesToSort.get(i).getLogText());
        }
    }

    /*public void sortLimit(ArrayList<Limit> limits) {
        System.out.println("sorting desc");
        this.limitsToSort = limits;
        Collections.sort(limitsToSort,DescriptionComparator.limitDescComparator());

        for (int i = 0; i < limitsToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + limitsToSort.get(i).getLogText());
        }
    }

    public void sortDebt(ArrayList<Debt> debts) {
        System.out.println("sorting desc");
        this.debtsToSort = debts;
        Collections.sort(debtsToSort,DescriptionComparator.debtDescComparator());

        for (int i = 0; i < debtsToSort.size(); i++) {
            int listNum = i + 1;
            System.out.println("\t" + listNum + ". " + debtsToSort.get(i).getLogText());
        }
    }*/


}
