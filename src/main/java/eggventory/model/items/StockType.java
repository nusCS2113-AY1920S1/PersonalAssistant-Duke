package eggventory.model.items;

import eggventory.commons.enums.StockProperty;
import eggventory.commons.exceptions.BadInputException;

import java.util.ArrayList;

//@@author Deculsion
/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class StockType {
    private String name;
    private ArrayList<Stock> stocks;

    /**
     * Creates a new StockType object. This overload should only be called from a Storage class.
     * @param name A unique name identifying the StockType.
     * @param savedFile A fully constructed ArrayList of Stock objects.
     */
    public StockType(String name, ArrayList<Stock> savedFile) {
        this.name = name;
        stocks = savedFile;
    }

    /**
     * Creates a new StockType object. StockType should only be instantiated from a StockList class.
     * @param name A unique name identifying the StockType.
     * @param isUniqueStock true if the Stock objects are a UniqueStock.
     */
    public StockType(String name, boolean isUniqueStock) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    /**
     * Creates a new StockType object. StockType should only be instantiated from a StockList class.
     * @param name A unique name identifying the StockType.
     */
    public StockType(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    /**
     * Adds a stock to the stockList.
     * @return True if item was added successfully.
     */
    public boolean addStock(String stockType, String stockCode, int quantity, String description)
            throws BadInputException {
        stocks.add(new CollectiveStock(stockType, stockCode, quantity, description));
        return true;
    }

    /**
     * Deletes a stock of the user's choice.
     * @param stockCode The code of the stock to be deleted.
     * @return true if some stockCode was found and the corresponding stock removed. false if none were found.
     */
    public Stock deleteStock(String stockCode) {

        Stock deletedStock;

        for (Stock stock : stocks) {
            if (stockCode.equals(stock.getStockCode())) {
                deletedStock = stock; //Not sure if this is a copy or not. Assumes unique stockCodes.
                stocks.remove(stock);
                return deletedStock;
            }
        }
        return null;
    }

    //@@author patwaririshab
    /**
     * Accesses and sets a new stockCode of a stock.
     * @param oldStockCode The current stockcode referring to this stock
     * @param newStockCode The new stockcode to change to
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockCode(String oldStockCode, String newStockCode) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(oldStockCode)) {
                stock.setStockCode(newStockCode);
                return stock;
            }
        }
        return null;
    }

    /**
     * Accesses and sets the quantity of a Stock.
     * @param stockCode StockCode of the stock to change
     * @param quantity New quantity of the stock to change
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockQuantity(String stockCode, int quantity) throws BadInputException {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setQuantity(quantity);
                return stock;
            }
        }
        return null;
    }

    /**
     * Accesses and updates loaned quantity of a stock.
     * @param stockCode The current stockcode reffering to this stock
     * @param quantity The new loaned quantity to change to
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockLoaned(String stockCode, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setLoaned(quantity);
                return stock;
            }
        }
        return null;
    }

    /**
     * Accesses and updates lost quantity of a stock.
     * @param stockCode The current stockcode reffering to this stock
     * @param quantity The new lost quantity to change to
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockLost(String stockCode, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setLost(quantity);
                return stock;
            }
        }
        return null;
    }

    /**
     * Accesses and updates stock description of a stock.
     * @param stockCode The current stockcode reffering to this stock
     * @param description The new description to change to
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockDescription(String stockCode, String description) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setDescription(description);
                return stock;
            }
        }
        return null;
    }

    /**
     * Accesses and updates minimum quantity of a stock.
     * @param stockCode The current stockcode reffering to this stock
     * @param quantity The new minimum quantity to change to
     * @return Stock if stockCode is found, else null
     */
    public Stock setStockMinimum(String stockCode, int quantity) throws BadInputException {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setMinimum(quantity);
                return stock;
            }
        }
        return null;
    }

    //@@author
    /**
     * Returns the entire stockList.
     * @return the stockList.
     */
    public ArrayList<Stock> getStockList() {
        return stocks;
    }

    /**
     * Returns a stock of the user's choice.
     * @param i the index of the stock selected.
     */
    public Stock getStock(int i) {
        return stocks.get(i);
    }

    /**
     * Returns a stock of the user's choice.
     * @param stockCode String which uniquely identifies a Stock.
     * @return If Stock exits, the Stock otherwise null.
     */
    public Stock getStock(String stockCode) {
        for (Stock stock: stocks) {
            if (stockCode.equals(stock.getStockCode())) {
                return stock;
            }
        }
        return null;
    }

    //@@author Deculsion
    /**
     * Gets the total number of stocks.
     * @return the number of stocks in this stockType.
     */
    public int getQuantity() {
        return stocks.size();
    }

    /**
     * Gets the name of this stockType.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    //@@author patwaririshab
    /**
     * Updates the name of the StockType.
     * @param newName String which uniquely identifies a StockType.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Updates the values of properties of a Stock.
     * @param stockCode String which uniquely identifies a Stock.
     * @param property The attribute of a Stock we want to update.
     * @param newValue The new value of the attribute to be updated.
     * @return The unedited Stock, for printing purpose.
     */
    public Stock setStock(String stockCode, StockProperty property, String newValue)
            throws BadInputException {
        switch (property) {
        case STOCKCODE:
            return this.setStockCode(stockCode, newValue);
        case QUANTITY:
            return this.setStockQuantity(stockCode, Integer.parseInt(newValue));
        case LOANED:
            return this.setStockLoaned(stockCode, Integer.parseInt(newValue));
        case LOST:
            return this.setStockLost(stockCode, Integer.parseInt(newValue));
        case DESCRIPTION:
            return this.setStockDescription(stockCode, newValue);
        case MINIMUM:
            return this.setStockMinimum(stockCode, Integer.parseInt(newValue));
        default:
            return null;
        }
    }

    //@@author cyanoei
    /**
     * Determines if any of the stocks in this stockType have the same stockCode.
     * @param stockCode the queried stockCode.
     * @return true if a stock in this stockType has that stockCode and false if none of the stocks have this stockCode.
     */
    public boolean isExistingStockCode(String stockCode) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                return true;
            }
        }
        return false; //If none of the stocks had the same code.
    }

    /**
     * Checks the entire StockType if any of the stocks contains a description equal to query.
     * @param query The word to search for in the description
     * @return The formatted stock details for the entire StockType
     *          if query is within the description, else an empty string.
     */
    public String queryStocksDescription(String query) {
        String output = "";
        for (Stock stock: stocks) {
            output += stock.containDescription(query);
        }
        return output;
    }

    //@@author Deculsion
    /**
     * A string of all the stock objects within this stocktype. Should only be called by Cli and StockList.
     * @return A string list of all the stock objects and their details.
     */
    public String toString() {

        //Do not show empty stockTypes.
        if (stocks.size() == 0) {
            return "";
        }

        StringBuilder ret = new StringBuilder();

        int i = 1;
        for (Stock stock : stocks) {
            ret.append(String.format("%d. ", i++)).append(stock.toString()).append("\n");
        }

        return ret.toString();

    }

    /**
     * Creates a String of all Stock objects under this StockType.
     * @return The String of all Stock objects.
     */
    public String saveDetailsString() {
        StringBuilder details = new StringBuilder();
        for (Stock stock : stocks) {
            details.append(stock.saveDetailsString()).append("\n");
        }
        return details.toString();
    }

    //@@author Raghav-B
    /**
     * Returns ArrayList of data of all stocks within this stocktype to be read
     * by GUI table.
     * @return ArrayList of data of stocks in this stocktype.
     */
    public ArrayList<ArrayList<String>> getDataAsArray() {
        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (Stock stock : stocks) {
            dataArray.add(stock.getDataAsArray());
        }
        return dataArray;
    }
    //@@author
}