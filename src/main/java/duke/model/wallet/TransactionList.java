package duke.model.wallet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static duke.commons.constants.DateConstants.DATE_FORMAT;

public class TransactionList {
    private Calendar calendarDate;
    private String currentDate ;
    private HashMap<String, ArrayList<Transaction>> transactionTracker = new HashMap<>();

    public TransactionList() {
        calendarDate = Calendar.getInstance();
        currentDate = DATE_FORMAT.format(calendarDate.getTime());
    }

    public Transaction deleteTransaction(String date, int index) {
        Transaction deletedTransaction = this.transactionTracker.get(date).get(index - 1);
        this.transactionTracker.get(date).remove(index - 1);
        return deletedTransaction;
    }

    public void deleteAllTransactionOnDate(String dateStr) {
        if (transactionTracker.containsKey(dateStr)) {
            this.transactionTracker.get(dateStr).clear();
        }
    }

    public void addTransaction(Transaction transaction) {
        String dateStr = transaction.getDate();
        if (!transactionTracker.containsKey(dateStr)) {
            transactionTracker.put(dateStr, new ArrayList<>());
        }
        transactionTracker.get(dateStr).add(transaction);
    }

    public ArrayList<Transaction> getTransactionList(String dateStr) {
        if (transactionTracker.containsKey(dateStr)) {
            return transactionTracker.get(dateStr);
        } else {
            transactionTracker.put(dateStr, new ArrayList<>());
            return transactionTracker.get(dateStr);
        }
    }

    public HashMap<String, ArrayList<Transaction>> getTransactionList() {
        return this.transactionTracker;
    }

    public HashMap<String, ArrayList<Transaction>> getTransactionTracker() {
        return transactionTracker;
    }

}
