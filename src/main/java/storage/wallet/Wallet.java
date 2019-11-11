package storage.wallet;

import duke.exception.DukeException;

import java.util.HashMap;

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
        this.initializeReceiptTracker();
    }

    /**
     * Default Constructor for Wallet Object.
     */
    public Wallet() {
        this.setBalance(0.00);
        this.initializeReceiptTracker();
    }

    /**
     * Adds a new Receipt Object into the receipts property of Wallet Object.
     * @param receipt Receipt Object to be stored
     */
    public void addReceipt(Receipt receipt) {
        this.receipts.addReceipt(receipt);
        this.balance -= receipt.getCashSpent();
    }

    /**
     * Intiailizes the ReceiptTracker.
     */
    void initializeReceiptTracker() {
        this.setReceipts(new ReceiptTracker());
        this.receipts.initializeMainReceiptTracker();
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
        if (this.balance < 0) {
            return 0.0;
        }
        return this.balance;
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
        return this.receipts;
    }

    /**
     * Getter for the totalCashSpent property of the ReceiptTracker Object housed in the Wallet Object.
     * @return Double representing the total cash spent as recorded by the ReceiptTracker
     */
    public double getTotalExpenses() {
        return this.receipts.getTotalExpenses();
    }

    /**
     * Getter for the folders property of the ReceiptTracker Object housed in the Wallet Object.
     * @return HashMap representing keys and the corresponding ReceiptTracker object
     */
    public HashMap<String, ReceiptTracker> getFolders() {
        return this.receipts.getFolders();
    }

    /**
     * Accessor for method getReceiptsByDate in ReceiptTracker.
     * @param date String representing the date to look for
     * @return ReceiptTracker containing all the Receipts for a particular Date
     */
    public ReceiptTracker getReceiptsByDate(String date) {
        return this.receipts.getReceiptsByDate(date);
    }

    /**
     * Accessor for method getReceiptByMonthYear in ReceiptTracker.
     * @param month int representing the month to look for
     * @param year int representing the year to look for
     * @return ReceiptTracker containing all the Receipts for a particular month and year
     */
    public ReceiptTracker getReceiptsByMonthYear(int month, int year) {
        return this.receipts.getReceiptsByMonthYear(month, year);
    }

    /**
     * Accessor for method getReceiptsByYear in ReceiptTracker.
     * @param year int representing the year to look for
     * @return ReceiptTracker containing all the Receipts for a particular year
     */
    public ReceiptTracker getReceiptsByYear(int year) {
        return this.receipts.getReceiptsByYear(year);
    }

    /**
     * Accessor for method getFolder in ReceiptTracker.
     * @param tag String to track by
     * @throws DukeException Error creating a folder to track tag.
     */
    public void addFolder(String tag) throws DukeException {
        this.receipts.addFolder(tag);
    }

    /**
     * Accessor for method removeFolder in ReceiptTracker.
     * @param tag String to be unregistered
     * @throws DukeException Error untracking a tag.
     */
    public void removeFolder(String tag) throws DukeException {
        this.receipts.removeFolder(tag);
    }
}
