package diyeats.model.wallet;

import java.math.BigDecimal;

//@@author GaryStu
/**
 * Wallet is a public class that deals with transactions and user's account.
 */
public class Wallet {
    private TransactionList transactions = new TransactionList();
    private Account account = new Account();

    /**
     * constructor for wallet object.
     */
    public Wallet() {
    }

    /**
     * getter for transactions.
     * @return transactions associated with this wallet.
     */
    public TransactionList getTransactions() {
        return transactions;
    }

    /**
     * getter for the account.
     * @return account associated with this wallet.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * getter for the account balance.
     * @return account balance in BigDecimal.
     */
    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    /**
     * setter for the amount of account balance.
     * @param accountBalance the string that specifies the account balance.
     */
    public void setAccountBalance(String accountBalance) {
        this.account.setAmount(new BigDecimal(accountBalance));
    }

    /**
     * update the account balance through transaction.
     * @param transaction the transaction used to update the account.
     */
    public void updateAccountBalance(Transaction transaction) {
        this.account.updateAccountBalance(transaction);
    }

    /**
     * update the account balance based on the passed wallet object.
     * @param wallet the wallet object passed.
     */
    public void updateAccountBalance(Wallet wallet) {
        this.transactions = wallet.getTransactions();
        this.account = wallet.getAccount();
    }

    /**
     * Check and add whether a payment transaction is larger than the current account balance.
     * @param payment the payment transaction specified.
     * @return <code>true</code> if there is sufficient balance.
     *         <code>false</code> if there is insufficient account balance.
     */
    public Boolean addPaymentTransaction(Payment payment) {
        if (this.account.isSufficientBalance(payment.getTransactionAmount())) {
            this.account.withdraw(payment.getTransactionAmount());
            this.transactions.addTransaction(payment);
            return true;
        }
        return false;
    }

}
