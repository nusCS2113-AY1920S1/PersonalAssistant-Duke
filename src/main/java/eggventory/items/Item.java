package eggventory.items;

/**
 * Represents a type of item in the inventory.
 * A type of item (eg. 560ohm resistor) may consist of many individual items (multiple resistors),
 * but they are all considered interchangeable and are not individually identified.
 * Within a type of item, some of the items may be marked as 'on loan', or 'lost'.
 */
public class Item {

    private String name;
    private int total;
    private int loaned;
    private int lost;

    /**
     * An item is first added with its name and total number. By default the loaned and lost numbers are 0.
     */
    public Item(String name, int total) {
        this.name = name;
        this.total = total;
        this.loaned = 0;
        this.lost = 0;
    }

    /**
     * Gets the name of the item.
     * @return name the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     * @param name the name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the total number of this item. Includes items lost and on loan.
     * @return total the name of the item.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the new total number of this item. To be used by 'change' or 'qty' commands to modify the number of items.
     * @param newTotal the new total number of items.
     */
    public void setTotal(int newTotal) {
        this.total = newTotal;
    }

    /**
     * Gets the number of this item that is on loan.
     * @return loaned the number of loaned items.
     */
    public int getLoaned() {
        return loaned;
    }

    /**
     * Sets the number of items on loan. To be used by the 'loan' command.
     * @param loaned the number of items on loan.
     */
    public void setLoaned(int loaned) {
        this.loaned = loaned;
    }

    /**
     * Gets the number of this item that is lost.
     * @return lost the number of lost items.
     */
    public int getLost() {
        return lost;
    }

    /**
     * Sets the number of items that have been lost. To be used by the 'lost' command.
     * @param lost the number of items lost.
     */
    public void setLost(int lost) {
        this.lost = lost;
    }

    /**
     * Calculates and returns the number of items available to the lab (not lost, not on loan).
     * @return the number of available items.
     */
    public int numAvailable() {
        return (total - loaned - lost);
    }

    /**
     * Prints the complete details of all the items of this type.
     * Format example: 560ohm Resistors: 280 available. 100 on loan. 20 lost. (400 total.)
     * To be used with the 'stock all' command.
     */
    public void printAll() {
        System.out.println(name + ": " + numAvailable() + " available. " + loaned + " on loan. "
                + lost + " lost. (" + total + " total.)");
    }

    /**
     * Prints the name and number of available items. Used as part of printing a list of available items.
     * Format example: 560ohm Resistors: 280
     * To be used with the 'stock' command.
     */
    public void printAvailable() {
        System.out.println(name + ": " + numAvailable());
    }

    /**
     * Prints the name and number of items on loan. Used as part of printing a list of items on loan.
     * Format example: 560ohm Resistors: 100
     * To be used with the 'stock loan' command.
     */
    public void printLoan() {
        System.out.println(name + ": " + lost);
    }

    /**
     * Prints the name and number of lost items. Used as part of printing a list of lost items.
     * Format example: 560ohm Resistors: 20
     * To be used with the 'stock lost' command.
     */
    public void printLost() {
        System.out.println(name + ": " + lost);
    }

}
