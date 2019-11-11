package diyeats.logic.dummy;

import diyeats.model.wallet.Account;
import diyeats.model.wallet.Payment;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.TransactionList;
import diyeats.model.wallet.Wallet;

import java.math.BigDecimal;

/**
 * This is a stub wallet class for testing purposes.
 */
public class DummyWallet extends Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    public DummyWallet() {
    }

    @Override
    public TransactionList getTransactions() {
        return transactions;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    @Override
    public void setAccountBalance(String accountBalance) {
        this.account.setAmount(new BigDecimal(accountBalance));
    }

    @Override
    public void updateAccountBalance(Transaction transaction) {
        this.account.updateAccountBalance(transaction);
    }

    @Override
    public void updateAccountBalance(Wallet wallet) {
        this.transactions = wallet.getTransactions();
        this.account = wallet.getAccount();
    }

    @Override
    public Boolean addPaymentTransaction(Payment payment) {
        if (this.account.isSufficientBalance(payment.getTransactionAmount())) {
            this.account.withdraw(payment.getTransactionAmount());
            this.transactions.addTransaction(payment);
            return true;
        }
        return false;
    }
}
