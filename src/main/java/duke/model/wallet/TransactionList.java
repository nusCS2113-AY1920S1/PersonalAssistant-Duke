package duke.model.wallet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static duke.commons.constants.DateConstants.DATE_FORMAT;

public class TransactionList {
    private Calendar calendarDate;
    private HashMap<LocalDate, ArrayList<Transaction>> transactionTracker = new HashMap<>();

    public TransactionList() {
    }

    public Transaction deleteTransaction(LocalDate date, int index) {
        Transaction deletedTransaction = this.transactionTracker.get(date).get(index - 1);
        this.transactionTracker.get(date).remove(index - 1);
        return deletedTransaction;
    }

    public void deleteAllTransactionOnDate(LocalDate date) {
        if (transactionTracker.containsKey(date)) {
            this.transactionTracker.get(date).clear();
        }
    }

    public void addTransaction(Transaction transaction) {
        LocalDate date = transaction.getDate();
        if (!transactionTracker.containsKey(date)) {
            transactionTracker.put(date, new ArrayList<>());
        }
        transactionTracker.get(date).add(transaction);
    }

    public ArrayList<Transaction> getTransactionList(LocalDate date) {
        if (transactionTracker.containsKey(date)) {
            return transactionTracker.get(date);
        } else {
            transactionTracker.put(date, new ArrayList<>());
            return transactionTracker.get(date);
        }
    }

    public HashMap<LocalDate, ArrayList<Transaction>> getTransactionList() {
        return this.transactionTracker;
    }

    public HashMap<LocalDate, ArrayList<Transaction>> getTransactionTracker() {
        return transactionTracker;
    }

}
