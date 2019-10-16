package eggventory;

import eggventory.items.StockType;

import java.util.ArrayList;

public class StockList {
    private ArrayList<StockType> stockList;

    /**
     * Constructs a new StockList object using an already existing stockList.
     * @param stockList ArrayList<> of StockType objects. There should already be a default "Uncategorised" StockType.
     */
    public StockList(ArrayList<StockType> stockList) {
        this.stockList = stockList;
    }

    /**
     * Constructs a new StockList object with one default StockType, "Uncategorised".
     */
    public StockList() {
        this.stockList = new ArrayList<>();
        this.stockList.add(new StockType("Uncategorised", false));
    }

    public void addStockType(String name) {
        stockList.add(new StockType(name, false));
    }

    /**
     * Checks whether mentioned stockType already exists.
     * @param stockType A String matching exactly the StockType to add the new Stock object under.
     * @param stockCode A unique String that identifies the Stock.
     * @param quantity Quantity of the stock.
     * @param description A String describing the nature of the Stock object.
     */
    public void addStock(String stockType, String stockCode, int quantity, String description) {
        for (StockType listType: stockList) {
            if (listType.getName().equals(stockType)) {
                listType.addStock(stockType, stockCode, quantity, description);
                return;
            }
        }

        // "Uncategorised" is always the first element on stockList.
        stockList.get(0).addStock("Uncategorised", stockCode, quantity, description);
    }

    /**
     * Deletes a Stock object from a list.
     * @param stockCode The unique String that identifies a Stock.
     */
    public void deleteStock(String stockCode) {
        for (StockType stockType : stockList) {
            stockType.deleteStock(stockCode);
        }
    }

    /**
     * Prints every stock within stocklist. Should only be called by Ui.
     * @return The string of the stocklist.
     */
    public String toString() {
        String ret = "";
        ret += "CURRENT INVENTORY\n";

        int quantity = getQuantity(); //Use the stockList size instead.
        for (int i = 0; i < quantity; i++) {
            ret += "------------------------\n";
            ret += stockList.get(i).toString() + "\n";
        }

        return ret;
    }

    /**
     * Saves the list into a String.
     * @return The String that will be directly saved into file.
     */
    public String saveDetailsString() {
        String details = "";

        for (StockType stocktype : stockList) {
            details += stocktype.saveDetailsString() + "\n";
        }

        return details;
    }

    /**
     * Returns a stockType from stockList if it exits else retuns a null StockType.
     * @param stockType The unique string that identifies a stockType
     * @return stockType of stockList
     */
    public StockType getStockType(String stockType) {
        StockType nullType = new StockType("NULL", true);
        for (StockType stType : stockList) {
            if (stType.getName().equals(stockType)) {
                return stType;
            }
        }
        return nullType;
    }

    public ArrayList<StockType> getList() {
        return stockList;
    }

    public int getQuantity() {
        return stockList.size();
    }

}
