package eggventory.items;

import java.util.ArrayList;

/**
 * A child of the Stock class.
 * A UniqueStock is a group of many items (eg. Arduino Uno) that share the same stockCode, description etc.,
 * but are also tracked individually (eg. Arduino Uno #31).
 */
public class UniqueStock extends Stock {

    private ArrayList<Item> uniqueStockList;

    /**
     * A stock is first added with its stockType, stockCode, description and quantity.
     * For Unique stock, the quantity determines the number of Item objects created.
     * By default the loaned and lost numbers are 0.
     *
     * @param stockType   The category the stock belongs to.
     * @param stockCode   The unique code that identifies the stock. (eg. 500ohm resistors are called 'R500')
     * @param quantity    The quantity (number of items) of this stock.
     * @param description The name of the stock. (eg. 500ohm resistor, mini breadboard)
     */
    public UniqueStock(String stockType, String stockCode, int quantity, String description) {
        super(stockType, stockCode, quantity, description);
        for (int i = 0; i < quantity; i++) {
            uniqueStockList.add(new Item(i+1));
        }
    }

    /**
     * Adds multiple unique items of this stock.
     * @param addQuantity The number of unique stocks being added.
     */
    public void addUniqueMultiple(int addQuantity) {
        int oldTotal = uniqueStockList.size();

        for (int i = 0; i < addQuantity; i++) {
            uniqueStockList.add(new Item(oldTotal + i));
        }

        super.setQuantity(oldTotal + addQuantity); //Updates the quantity
    }

    /**
     * Formats all stock details appropriately for Ui output.
     * @return the stock details string.
     */
    @Override
    public String toString() {
        return getStockType() + " | " + getStockCode() + " | " + getQuantity() + " | " + getDescription();
    }

    //TODO: Provide a 'detailed' version of the print string to show individual items.


    //TODO: Update both print and save method in Item class, and iterate through all of them here.
    //Should be similar to StockType printing methods.
    /**
     * Formats all stock details appropriately to be saved to file.
     * @return the string to save.
     */
    @Override
    public String saveDetailsString() {
        return getStockType() + "/" + getStockCode() + "/" + getQuantity() +
                "/" + getDescription() + "/" + getMinimum();
    }

    //way to access individual stock
    //get/set id
    //add stock
        //update total quantity ++
    //update loan/lost status
        //update total loan/lost by +/-
    //delete stock
        //update total quantity --
        //check if min, give warning.


}
