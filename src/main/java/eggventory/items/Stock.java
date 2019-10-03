package eggventory.items;

/**
 * Represents a stock of a particular item in the inventory.
 * A type of stock (eg. 560ohm resistor) may consist of many individual items (multiple resistors),
 * but they are all considered interchangeable and are not individually identified.
 * Within a stock, some of the items may be marked as 'on loan', or 'lost'.
 * TODO: Finish up the comments on this class after finalising the glossary.
 */
public class Stock {

    private String stockType;
    private String stockCode;
    private int quantity;
    private String description;
    private int loaned;
    private int lost;

    /**
     * An stock is first added with its stockType, stockCode, description and quantity.
     * By default the loaned and lost numbers are 0.
     */
    public Stock(String stockType, String stockCode, int quantity, String description) {
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.quantity = quantity;
        this.description = description;
        this.loaned = 0;
        this.lost = 0;
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

    /**
     * Gets the total number of this stock. Includes items lost and on loan.
     * @return total the name of the stock.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the new total number of this stock. To be used by 'change' or 'qty' commands to modify the number.
     * @param newTotal the new total number of items.
     */
    public void setQuantity(int newTotal) {
        this.quantity = newTotal;
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
     * Calculates and returns the number of this stock available to the lab (not lost, not on loan).
     * @return the number of available items.
     */
    public int numAvailable() {
        return (quantity - loaned - lost);
    }

    /**
     * Formats all stock details appropriately for Ui output.
     * @return the stock details.
     */
    @Override
    public String toString() {
        return stockType + " | " + stockCode + " | " + quantity + " | " + description;
    }

    public String saveDetailsString() {
        return stockType + "/" + stockCode + "/" + quantity + "/" + description;
    }

    //TODO: Fix methods below for new UI.print() implementation.

    /**
     * Prints the complete details of all the items of this type.
     * Format example: 560ohm Resistors: 280 available. 100 on loan. 20 lost. (400 total.)
     * To be used with the 'stock all' command.
     */
    public void printAll() {
        System.out.println(description + ": " + numAvailable() + " available. " + loaned + " on loan. "
                + lost + " lost. (" + quantity + " total.)");
    }

    /**
     * Prints the name and number of available items. Used as part of printing a list of available items.
     * Format example: 560ohm Resistors: 280
     * To be used with the 'stock' command.
     */
    public void printAvailable() {
        System.out.println(description + ": " + numAvailable());
    }

    /**
     * Prints the name and number of items on loan. Used as part of printing a list of items on loan.
     * Format example: 560ohm Resistors: 100
     * To be used with the 'stock loan' command.
     */
    public void printLoan() {
        System.out.println(description + ": " + lost);
    }

    /**
     * Prints the name and number of lost items. Used as part of printing a list of lost items.
     * Format example: 560ohm Resistors: 20
     * To be used with the 'stock lost' command.
     */
    public void printLost() {
        System.out.println(description + ": " + lost);
    }

}
