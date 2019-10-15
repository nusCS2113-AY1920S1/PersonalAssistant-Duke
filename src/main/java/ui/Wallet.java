package ui;

import java.util.ArrayList;

public class Wallet {
    private Double balance;
    private ReceiptTracker receipts;

    /**
     * Complete Constructor for Wallet Object.
     * @param balance Double to be set for balance property of Wallet Object
     * @param receipts ArrayList to be set for receipts property of Wallet Object
     */
    public Wallet(Double balance, ReceiptTracker receipts) {
        this.setBalance(balance);
        this.setReceipts(receipts);
    }

    /**
     * Partial Constructor for Wallet Object.
     * @param balance Double to be set for balance property of Wallet Object
     */
    public Wallet(Double balance) {
        this.setBalance(balance);
        this.setReceipts(new ReceiptTracker());
    }

    /**
     * Default Constructor for Wallet Object.
     */
    public Wallet() {
        this.setBalance(0.00);
        this.setReceipts(new ReceiptTracker());
    }

    /**
     * Adds a new Receipt Object into the receipts property of Wallet Object.
     * @param receipt Receipt Object to be stored
     */
    public void addReceipt(Receipt receipt) {
        this.receipts.add(receipt);
    }

    // -- Boolean Functions

    /**
     * Checks if the Wallet contains any receipts.
     * @return true if receipts property is an empty list, false otherwise
     */
    public Boolean isReceiptsEmpty() {
        return this.getReceipts().isEmpty();
    }

    // -- Setters & Getters
    /**
     * Setter for balance property of Wallet Object.
     * @param input The value to be set as balance
     */
    public void setBalance(Double input) {
        this.balance = input;
    }

    /**
     * Getter for balance property of Wallet Object.
     * @return Double value corresponding to balance property in Wallet Object
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * Setter for receipts property of Wallet Object.
     * @param receipts ArrayList of receipts property of Wallet Object
     */
    public void setReceipts(ReceiptTracker receipts) {
        this.receipts = receipts;
    }

    /**
     * Getter for receipts property of Wallet Object.
     * @return ArrayList of receipts property in Wallet Object
     */
    public ReceiptTracker getReceipts() {
        return receipts;
    }
}
