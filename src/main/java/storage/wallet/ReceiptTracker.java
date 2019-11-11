package storage.wallet;

import duke.exception.DukeException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptTracker extends ArrayList<Receipt> {
    private HashMap<String, ReceiptTracker> folders;
    private Double nettCashSpent;
    private Double budget;

    /**
     * Overloaded Constructor for ReceiptTracker.
     * @param receiptList List of receipts to be loaded into the ReceiptTracker.
     */
    public ReceiptTracker(ArrayList<Receipt> receiptList) {
        this.setFolders(new HashMap<>());
        this.addAll(receiptList);
        try {
            this.addFolder("Income");
            this.addFolder("Expenditure");
        } catch (DukeException e) {
            e.printStackTrace();
        }
        this.updateNettCashSpent();
        this.setFolders(new HashMap<>());
    }

    /**
     * Default Constructor for ReceiptTracker.
     */
    public ReceiptTracker() {
        this.updateNettCashSpent();
        this.setFolders(new HashMap<>());
    }

    public void initializeMainReceiptTracker() {
        this.getFolders().put("Income", new ReceiptTracker());
        this.getFolders().put("Expenses", new ReceiptTracker());
    }

    /**
     * Wrapper to add a new Receipt Object to the Receipt Tracker.
     * @param receipt Receipt Object to be added.
     */
    public void addReceipt(Receipt receipt) {
        this.add(receipt);
        for (String tag : receipt.getTags()) {
            if (isRegisteredTag(tag)) {
                Double currTotalCashSpent = folders.get(tag).getNettCashSpent();
                folders.get(tag).addReceipt(receipt);
                folders.get(tag).setNettCashSpent(currTotalCashSpent + receipt.getCashSpent());
            }
        }
        this.updateNettCashSpent();
    }

    /**
     * Updates totalCashSpent property of this ReceiptTracker Object.
     */
    public void updateNettCashSpent() {
        this.setNettCashSpent(this.sumReceipts());
    }

    /**
     * Sums the cashSpent of all the Receipts, subtracting any cashGained from IncomeReceipts.
     * @return Double representing the totalCashSpent
     */
    public Double sumReceipts() {
        Double sum = 0.0;
        for (Receipt receipt : this) {
            sum += receipt.getCashSpent();
        }
        return sum;
    }

    /**
     * Registers a tag to be tracked.
     * @param tag String to be registered into the folders property of ReceiptTracker Object
     * @throws DukeException Folder already exists
     */
    public void addFolder(String tag) throws DukeException {
        if (isRegisteredTag(tag)) {
            throw new DukeException("Category already exists!");
        }
        ArrayList<Receipt> taggedReceipts = getReceiptsByTag(tag);
        if (taggedReceipts.size() < this.size()) {
            this.getFolders().put(tag, new ReceiptTracker(taggedReceipts));
        }
    }

    /**
     * Unregisters a tag.
     * @param tag String to be unregistered
     * @throws DukeException Folder doesn't exist
     */
    public void removeFolder(String tag) throws DukeException {
        if (!isRegisteredTag(tag)) {
            throw new DukeException("<" + tag + "> is not being tracked!");
        }
        this.getFolders().remove(tag);
    }

    /**
     * Find all receipts that are tagged with a specific String.
     * @param tag Specific String to be filtered with.
     * @return ArrayList containing all the Receipts with the specific tag
     */
    public ReceiptTracker getReceiptsByTag(String tag) {
        ReceiptTracker taggedReceipts = new ReceiptTracker();
        taggedReceipts.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.containsTag(tag)) {
                taggedReceipts.addReceipt(receipt);
            }
        }
        return taggedReceipts;
    }

    /**
     * Find all the expenses more than or equal to the cash input.
     * @param amount Specific String to be filtered with.
     * @return ArrayList containing all the Receipts with all the major expenses
     * @throws DukeException not able to get majorexpense
     */
    public ReceiptTracker getMajorExpenses(String amount) throws DukeException {
        int input = Integer.parseInt(amount);
        ReceiptTracker expenseReceipts = new ReceiptTracker();
        expenseReceipts.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.getCashSpent() >= input) {
                expenseReceipts.addReceipt(receipt);
            }
        }
        if (expenseReceipts.isEmpty()) {
            throw new DukeException("Unable to get major expenses for this case");
        }
        return expenseReceipts;
    }

    /**
     * Find all the expenses more than or equal to $100.
     * @return ArrayList containing all the receipts with expenses above/equal to $100
     * @throws DukeException no receipt in the list that is of $100 or above
     */
    public ReceiptTracker getMajorReceipts() throws DukeException {
        ReceiptTracker receipts = new ReceiptTracker();
        receipts.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.getCashSpent() >= 100) {
                receipts.addReceipt(receipt);
            }
        }
        if (receipts.isEmpty()) {
            throw new DukeException("No major expenses above/equal to $100 was found");
        }
        return receipts;
    }

    /**
     * Find all receipts that corresponds to the specific date.
     * @param date Specific String to be filtered with
     * @return ArrayList containing all the Receipts with the specific date
     */
    public ReceiptTracker getReceiptsByDate(String date) {
        ReceiptTracker dateReceipts = new ReceiptTracker();
        dateReceipts.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.equalsDate(date)) {
                dateReceipts.addReceipt(receipt);
            }
        }
        return dateReceipts;
    }


    /**
     * Finds all the receipts that corresponds to that month and year.
     * @param month is the month given by the user
     * @param year is the year given by the user
     * @return ArrayList containing all the receipts which corresponds to year and month
     */
    public ReceiptTracker getReceiptsByMonthYear(int month, int year) {
        ReceiptTracker receiptByMonthYear = new ReceiptTracker();
        receiptByMonthYear.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if ((receipt.getDate().getMonthValue() == month) && (receipt.getDate().getYear() == year)) {
                receiptByMonthYear.addReceipt(receipt);
            }
        }
        return receiptByMonthYear;
    }

    /**
     * Finds all the receipts that corresponds to that year.
     * @param year is the year given by the user
     * @return ReceiptTracker containing all the receipts which corresponds to the year given by user
     */
    public ReceiptTracker getReceiptsByYear(int year) {
        ReceiptTracker receiptByYear = new ReceiptTracker();
        receiptByYear.initializeMainReceiptTracker();
        for (Receipt receipt : this) {
            if (receipt.getDate().getYear() == year) {
                receiptByYear.addReceipt(receipt);
            }
        }
        return receiptByYear;
    }


    // -- Boolean Functions
    /**
     * Checks if a tag has been registered previously.
     * @param tag String representing folder to be checked
     * @return true if a folder in the folders property has the name tag, false otherwise
     */
    public boolean isRegisteredTag(String tag) {
        return this.getFolders().containsKey(tag);
    }

    // -- Setters & Getters

    /**
     * Retrieves the totalCashSpent by a specific tag.
     * @param tag String representing the tag to filter by
     * @return Double, the total amount spent on a given tag
     */
    public double getCashSpentByTag(String tag) {
        if (isRegisteredTag(tag)) {
            return this.getFolders().get(tag).getNettCashSpent();
        } else {
            ReceiptTracker temp = new ReceiptTracker(this.getReceiptsByTag(tag));
            return temp.getNettCashSpent();
        }
    }

    /**
     * Setter for the folders property of the ReceiptTracker Object.
     * @param folders HashMap to be set as the folders property of ReceiptTracker Object
     */
    public void setFolders(HashMap<String, ReceiptTracker> folders) {
        this.folders = folders;
    }

    /**
     * Getter for the folders property of the ReceiptTracker Object.
     * @return HashMap representing the folders property of ReceiptTracker Object
     */
    public HashMap<String, ReceiptTracker> getFolders() {
        return folders;
    }

    /**
     * Setter for the totalCashSpent property of the ReceiptTracker Object.
     * @param nettCashSpent Double amount to be set as the totalCashSpent property of ReceiptTracker Object
     */
    public void setNettCashSpent(Double nettCashSpent) {
        this.nettCashSpent = nettCashSpent;
    }

    /**
     * Getter for totalCashSpent property of ReceiptTracker Object.
     * @return Double representing the totalCashSpent property of ReceiptTracker Object
     */
    public Double getNettCashSpent() {
        return nettCashSpent;
    }

    /**
     * Deletes a receipt via its index.
     * @param index Index of the receipt to be deleted
     */
    public void deleteReceiptsByIndex(int index) {
        this.remove(index);
    }

    /**
     * Prints all the receipts stored in the ReceiptTracker Object.
     * @return String containing all the receipts to be printed to the User
     */
    public String getPrintableReceipts() {
        StringBuilder outputStr = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        for (int index = 0; index < this.size(); ++index) {
            try {
                outputStr.append(index + 1)
                        .append(". ")
                        .append(this.get(index).getTags())
                        .append(" $");
                if (this.get(index).getCashSpent() >= 0) {
                    outputStr.append(decimalFormat.format(this.get(index).getCashSpent()));
                } else {
                    outputStr.append(decimalFormat.format(-this.get(index).getCashSpent()));
                }
                outputStr.append(" ")
                        .append(this.get(index).getDate())
                        .append("\n")
                ;
            } catch (Exception e) {
                outputStr.append("Unable to print Receipt ")
                        .append(String.valueOf(index + 1))
                        .append("\n")
                ;
            }
        }
        return outputStr.toString();
    }

    /**
     * Gets the total sum of all the Income Receipts.
     * @return Double representing said sum
     */
    public Double getTotalIncome() {
        if (isRegisteredTag("Income")) {
            return -this.getFolders().get("Income").getNettCashSpent();
        } else if (this.nettCashSpent < 0) {
            return -this.nettCashSpent;
        } else {
            return 0.0;
        }

    }

    /**
     * Gets the total sum of all the Spending Receipts.
     * @return Double representing said sum
     */
    public Double getTotalExpenses() {
        if (isRegisteredTag("Expenses")) {
            return this.getFolders().get("Expenses").getNettCashSpent();
        } else if (this.nettCashSpent >= 0) {
            return this.nettCashSpent;
        } else {
            return 0.0;
        }
    }
      
    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
