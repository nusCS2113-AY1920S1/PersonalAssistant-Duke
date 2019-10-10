package moomoo.task;

import java.util.ArrayList;

public class TransactionList {
    private ArrayList<Transaction> transList;

    public TransactionList() {
        transList = new ArrayList<Transaction>();
    }

    public TransactionList(ArrayList<Transaction> inList) {
        this.transList = inList;
    }
}
