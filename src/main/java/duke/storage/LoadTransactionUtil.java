package duke.storage;

import duke.model.wallet.Transaction;
import duke.model.wallet.TransactionList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * load transactionList based on new transaction.
 */
public class LoadTransactionUtil {

    /**
     * load new transaction to transaction transactionList.
     * @param transactions transactionList in which the transaction is to be added to.
     * @param newTransaction the new transaction to be added.
     */
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
