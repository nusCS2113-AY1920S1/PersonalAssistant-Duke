package eggventory.items;

import java.util.ArrayList;

public class UniqueStock extends Stock {

    private ArrayList<CollectiveStock> uniqueStockList;

    /**
     * A child of the Stock class.
     * A UniqueStock is a group of many items (eg. Arduino Uno) that share the same stockCode, description etc.,
     * but are also tracked individually (eg. Arduino Uno #31).
     * A stock is first added with its stockType, stockCode, description and quantity.
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
            uniqueStockList.add(new CollectiveStock(stockType, stockCode, 1, description));
            //TODO: Item class - unique id, loan/lost status.
        }
    }

    //way to access individual stonk
    //get/set id
    //add stonk
        //update total quantity ++
    //update loan/lost status
        //update total loan/lost by +/-
    //delete stonk
        //update total quantity --
        //check if min, give warning.


}
