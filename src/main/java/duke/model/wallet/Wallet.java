package duke.model.wallet;

import java.math.BigDecimal;

public class Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    public Wallet() {
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionList getTransactions() {
        return transactions;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    public void setAccountBalance(int accountBalance) {
        this.account.setAmount(new BigDecimal(accountBalance));
    }

    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            this.account.withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            this.account.deposit(transactionAmount);
        }
    }

    public void addTransactions(int cost, String date) {
        this.transactions.addTransaction(new Transaction(Integer.toString(cost),date));
        this.account.withdraw(new BigDecimal(cost));
    }
}
