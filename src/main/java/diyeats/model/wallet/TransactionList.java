package diyeats.model.wallet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

//@@author GaryStu
/**
 * TransactionList is a public class that keeps track of transactions based on its date.
 */
public class TransactionList {
    private HashMap<LocalDate, ArrayList<Transaction>> transactionTracker = new HashMap<>();

    public TransactionList() {
    }

    /**
     * add transaction to transactionTracker.
     * @param transaction the transaction to be added.
     */
    public void addTransaction(Transaction transaction) {
        LocalDate date = transaction.getDate();
        if (!transactionTracker.containsKey(date)) {
            transactionTracker.put(date, new ArrayList<>());
        }
        transactionTracker.get(date).add(transaction);
    }

    /**
     * gets a list of transactions based on a date.
     * @param date the date in which the list of transactions is to be taken from.
     * @return the transaction list that has been taken.
     */
    public ArrayList<Transaction> getTransactionList(LocalDate date) {
        if (transactionTracker.containsKey(date)) {
            return transactionTracker.get(date);
        } else {
            transactionTracker.put(date, new ArrayList<>());
            return transactionTracker.get(date);
        }
    }

    /**
     * the getter of the transactionTracker.
     * @return the transactionTracker to be returned.
     */
    public HashMap<LocalDate, ArrayList<Transaction>> getTransactionList() {
        return this.transactionTracker;
    }

}
