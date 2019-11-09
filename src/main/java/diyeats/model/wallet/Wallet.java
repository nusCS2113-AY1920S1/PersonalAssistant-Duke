package diyeats.model.wallet;

import java.math.BigDecimal;

public class Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    public Wallet() {
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

    public void setAccountBalance(String accountBalance) {
        this.account.setAmount(new BigDecimal(accountBalance));
    }

    public void updateAccountBalance(Transaction transaction) {
        this.account.updateAccountBalance(transaction);
    }

    public void updateAccountBalance(Wallet wallet) {
        this.transactions = wallet.getTransactions();
        this.account = wallet.getAccount();
    }

    public Boolean addPaymentTransaction(Payment payment) {
        if (this.account.isSufficientBalance(payment.getTransactionAmount())) {
            this.account.withdraw(payment.getTransactionAmount());
            this.transactions.addTransaction(payment);
            return true;
        }
        return false;
    }
}
