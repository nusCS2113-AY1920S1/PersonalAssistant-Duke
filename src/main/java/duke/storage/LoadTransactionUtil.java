package duke.storage;

import duke.model.Transaction;
import duke.model.TransactionList;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadTransactionUtil {

    public static void load(TransactionList transactions, Transaction newTransaction) {
        HashMap<String, ArrayList<Transaction>> transactionTracker = transactions.getTransactionTracker();
        String transactionDate = newTransaction.getDate();
        if (!transactionTracker.containsKey(transactionDate)) {
            transactionTracker.put(transactionDate, new ArrayList<>());
            transactionTracker.get(transactionDate).add(newTransaction);
        } else {
            transactionTracker.get(transactionDate).add(newTransaction);
        }
    }
}
