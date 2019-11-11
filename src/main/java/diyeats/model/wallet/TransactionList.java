package diyeats.model.wallet;

import java.time.LocalDate;
import java.util.ArrayList;
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

    //To facilitate undo
    public Transaction deleteTransaction(LocalDate date) {
        Transaction deletedTransaction = this.transactionTracker
                .get(date).get(this.transactionTracker.get(date).size() - 1);
        this.transactionTracker.get(date).remove(this.transactionTracker.get(date).size() - 1);
        return deletedTransaction;
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
    public HashMap<LocalDate, ArrayList<Transaction>> getTransactionTracker() {
        return this.transactionTracker;
    }

    /**
     * delete the transaction specified by the index and its date.
     * @param date the date of the deleted transaction.
     * @param index the index of the deleted transaction.
     * @return Returns the transaction that was deleted.
     */
    public Transaction delete(LocalDate date, int index) {
        Transaction deletedTransaction = this.transactionTracker.get(date).get(index - 1);
        this.transactionTracker.get(date).remove(index - 1);
        return deletedTransaction;
    }
}
