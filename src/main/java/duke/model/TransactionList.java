package duke.model;

import duke.model.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TransactionList {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar calendarDate = Calendar.getInstance();
    private String currentDate = dateFormat.format(calendarDate.getTime());
    private HashMap<String, ArrayList<Transaction>> transactionTracker = new HashMap<>();

    public TransactionList() {
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

    public void addTransaction(Transaction transaction, User user) {
        String dateStr = transaction.getDate();
        if (!transactionTracker.containsKey(dateStr)) {
            transactionTracker.put(dateStr, new ArrayList<Transaction>());
        }
        transactionTracker.get(dateStr).add(transaction);
        user.updateAccountBalance(transaction);
    }

    public ArrayList<Transaction> getTransactionList(String dateStr) {
        if (transactionTracker.containsKey(dateStr)) {
            return transactionTracker.get(dateStr);
        } else {
            transactionTracker.put(dateStr, new ArrayList<>());
            return transactionTracker.get(dateStr);
        }
    }

    public HashMap<String, ArrayList<Transaction>> getTransactionTracker() {
        return transactionTracker;
    }

}
