package moomoo.task;

import java.util.ArrayList;

public class TransactionList {
    private ArrayList<Expenditure> transList;

    public TransactionList() {
        transList = new ArrayList<Expenditure>();
    }

    public TransactionList(ArrayList<Expenditure> inList) {
        this.transList = inList;
    }
}
