package diyeats.logic.dummy;

import diyeats.model.wallet.Account;
import diyeats.model.wallet.Payment;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.TransactionList;
import diyeats.model.wallet.Wallet;

import java.math.BigDecimal;

public class DummyWallet extends Wallet {

    public DummyWallet() {
    }

    @Override
    public TransactionList getTransactions() {
        return null;
    }

    @Override
    public Account getAccount() {
        return null;
    }

    @Override
    public BigDecimal getAccountBalance() {
        return null;
    }

    @Override
    public void setAccountBalance(String accountBalance) {
    }

    @Override
    public void updateAccountBalance(Transaction transaction) {
    }

    @Override
    public void updateAccountBalance(Wallet wallet) {
    }

    @Override
    public Boolean addPaymentTransaction(Payment payment) {
        return true;
    }
}
