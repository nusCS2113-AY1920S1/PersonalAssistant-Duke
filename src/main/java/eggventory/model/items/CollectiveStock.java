package eggventory.model.items;

//@@author cyanoei
import eggventory.commons.exceptions.BadInputException;

/**
 Child of the Stock class.
 * A CollectiveStock (eg. 330ohm resistor) may consist of many individual items (multiple resistors),
 * but they are all considered interchangeable and are not individually identified.
 */
public class CollectiveStock extends Stock {
    /**
     * A stock is first added with its stockType, stockCode, description and quantity.
     * By default the loaned and lost numbers are 0.
     *
     * @param stockType The category the stock belongs to.
     * @param stockCode The unique code that identifies the stock. (eg. 500ohm resistors are called 'R500')
     * @param quantity  The quantity (number of items) of this stock.
     * @param description The name of the stock. (eg. 500ohm resistor, mini breadboard)
     */
    public CollectiveStock(String stockType, String stockCode, int quantity, String description)
            throws BadInputException {
        super(stockType, stockCode, quantity, description);

    }

    //For now, all the methods we need are implemented by the Stock class.

}
