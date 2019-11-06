package eggventory.model.items;


import eggventory.commons.exceptions.BadInputException;
import eggventory.model.loans.Loan;

import java.util.ArrayList;

//@@author yanprosobo
/**
 * An abstract class representing a type of item that the lab keeps and is able to loan out.
 * Children classes are CollectiveStock and UniqueStock.
 * A stock is first added with its stockType, stockCode, description and quantity.
 * Within a stock, some of the items may be marked as 'on loan', or 'lost'.
 * TODO: Finish up the comments on this class after finalising the glossary.
 */
public class Stock {

    private String stockType;
    private String stockCode;
    protected int quantity;
    private String description;
    private int loaned;
    private int lost;
    private int minimum; //Minimum quantity the lab should maintain.
    //private int loanLimit; //Maximum quantity an individual can loan. To implement in the future.
    private ArrayList<Loan> stockLoans;

    /**
     * An stock is first added with its stockType, stockCode, description and quantity.
     * By default the loaned and lost numbers are 0. They can be updated later.
     * By default the minimum quantity is 0. This can be updated later.
     *
     * @param stockType The category the stock belongs to.
     * @param stockCode The unique code that identifies the stock. (eg. 500ohm resistors are called 'R500')
     * @param quantity The quantity (number of items) of this stock.
     * @param description The name of the stock. (eg. 500ohm resistor, mini breadboard.
     */
    public Stock(String stockType, String stockCode, int quantity, String description)
            throws BadInputException {
        quantitySanityCheck(quantity);
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.description = description;
        this.loaned = 0;
        this.lost = 0;
        this.minimum = 0;
    }

    /**
     * Gets the stock type (category) of the stock.
     * @return the stock type.
     */
    public String getStockType() {
        return stockType;
    }

    //Should not allow updating of stockType for now (so no setter)

    /** Gets the stock code.
     * @return the stock code.
     */
    public String getStockCode() {
        return stockCode;
    }

    /**
     * Changes the stock code (if there was an error in entry).
     * @param stockCode the new stock code
     */
    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    /**
     * Gets the name of the stock.
     * @return the name of the stock.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the name of the stock.
     * @param description the name of the stock.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    //Note: The access to the quantity attribute might have to be changed in the future.

    /**
     * Gets the total number of this stock. Includes items lost and on loan.
     * @return total the total quantity of that stock.
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * Sets the new total number of this stock. To be used by 'change' or 'qty' commands to modify the number.
     * @param newTotal the new total number of items.
     */
    public void setQuantity(int newTotal) throws BadInputException {
        quantitySanityCheck(newTotal);
        this.quantity = newTotal;
    }

    /**
     * Checks if quantity input is reasonable.
     * @param quantity the quantity of the stock.
     * @throws BadInputException if the quantity is negative.
     */
    public void quantitySanityCheck(int quantity) throws BadInputException {
        if (quantity < 0) {
            throw new BadInputException("Sorry, the quantity cannot be negative!");
        }
    }

    /**
     * Gets the number of this stock that is on loan.
     * @return loaned the number of loaned items.
     */
    public int getLoaned() {
        return loaned;
    }


    /**
     * Sets the number of this stock on loan. To be used by the 'loan' command.
     * @param loaned the number of items on loan.
     */
    public void setLoaned(int loaned) {
        this.loaned = loaned;
    }

    /**
     * Gets the number of this stock that is lost.
     * @return lost the number of lost items.
     */
    public int getLost() {
        return lost;
    }


    /**
     * Sets the number of this stock that have been lost. To be used by the 'lost' command.
     * @param lost the number of items lost.
     */
    public void setLost(int lost) {
        this.lost = lost;
    }

    /**
     * Gets the minimum quantity of stock that the lab wishes to maintain.
     * @return The minimum quantity.
     */
    public int getMinimum() {
        return minimum;
    }


    /**
     * Updates the minimum quantity of stock that the lab wishes to maintain. To be implemented in the future.
     * @param minimum The minimum quantity.
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Calculates and returns the number of this stock available to the lab (not lost, not on loan).
     * @return the number of available items.
     */
    public int numAvailable() {
        return (quantity - loaned - lost);
    }

    /**
     * Formats all stock details appropriately for Cli output. Should only be called by Cli and StockType.
     * @return the stock details string.
     */
    @Override
    public String toString() {
        return stockType + " | " + stockCode + " | " + quantity + " | " + description;
    }

    /**
     * Formats all stock details appropriately to be saved to file.
     * @return the string to save.
     */
    public String saveDetailsString() {
        return stockType + "," + stockCode + "," + quantity + "," + description + "," + minimum;
    }

    //@@author Raghav-B
    /**
     * Returns data of Stock in format that can be read by GUI's TableView.
     * @return ArrayList containing data of Stock.
     */
    public ArrayList<String> getDataAsArray() {
        ArrayList<String> dataArray = new ArrayList<>();
        dataArray.add(stockType);
        dataArray.add(stockCode);
        dataArray.add(String.valueOf(quantity));
        dataArray.add(description);
        return dataArray;
    }
    //@@author
}
