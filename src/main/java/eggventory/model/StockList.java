package eggventory.model;

import eggventory.commons.enums.StockProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.items.Stock;
import eggventory.model.items.StockType;
import eggventory.ui.TableStruct;

import java.util.ArrayList;
import java.util.Collections;

//@@author Deculsion
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

    /**
     * Gets the whole stockList. Note: technically doing using this method will violate OOP.
     * @return the list.
     */
    public ArrayList<StockType> getList() {
        return stockList;
    }

    /**
     * Adds a new StockType to the list.
     * @param name Name of new stocktype being added.
     */
    public void addStockType(String name) {
        stockList.add(new StockType(name, false));
    }

    //@@author cyanoei
    /**
     * Prints every stock within stocklist. Should only be called by Ui.
     * Deletes a StockType object, and all the stocks under it.
     * @param name Name of StockType to be deleted
     * @return The object if it was deleted, null if nothing waas deleted.
     */
    public StockType deleteStockType(String name) {
        StockType deleted;

        for (StockType stocktype : stockList) {
            if (stocktype.getName().equals(name)) {
                deleted = stocktype;
                stockList.remove(stocktype);
                return deleted;
            }
        }

        return null;
    }

    //@@author
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

    /**
     * Gets the total number of stockTypes in this stockList. Not to be confused with getStockQuantity.
     * @return the number of stockTypes.
     */
    public int getStockTypeQuantity() { //The number of stockTypes in the list.
        return stockList.size();
    }

    /**
     * Adds a Stock to the specified StockType in the list.
     * @param stockType A String matching exactly the StockType to add the new Stock object under.
     * @param stockCode A unique String that identifies the Stock.
     * @param quantity Quantity of the stock.
     * @param description A String describing the nature of the Stock object.
     */
    public void addStock(String stockType, String stockCode, int quantity, String description)
            throws BadInputException {
        for (StockType listType: stockList) {
            if (listType.getName().equals(stockType)) {
                listType.addStock(stockType, stockCode, quantity, description);
                return;
            }
        }

        // "Uncategorised" is always the first element on stockList.
        stockList.get(0).addStock("Uncategorised", stockCode, quantity, description);
    }

    //@@author cyanoei
    /**
     * Deletes a Stock object from a list.
     * @param stockCode The unique String that identifies a Stock.
     * @return the stock that was deleted, for printing purposes.
     */
    public Stock deleteStock(String stockCode) {
        Stock deleted;
        for (StockType stockType : stockList) {
            deleted = stockType.deleteStock(stockCode);
            if (deleted !=  null) { //If something was deleted
                return deleted;
            }
        }
        return null;
    }

    //@@author cyanoei

    /**
     * Formats an error message for the case of editing to a repeated stockCode.
     * @param newStockCode the new stockCode chosen by the user.
     * @return the error message.
     */
    public String repeatedStockCodeOutput(String newStockCode) {
        return String.format("Sorry, the stock code \"%s\" is already assigned to a stock in the system. "
                + "Please enter a different stock code.", newStockCode);
    }

    /**
     * Formats an error message for the case of trying to edit a nonexistent stockCode.
     * @param stockCode the stockCode which does not exist in the system.
     * @return the error message.
     */
    public String nonexistentStockCodeOutput(String stockCode) {
        return String.format("Sorry, the stock code \"%s\" cannot be found in the system. "
                + "Please enter a different stock code.", stockCode);
    }

    //@@author patwaririshab
    /**
     * Edits a Stock object in a StockList.
     * @param stockCode The unique String that identifies a Stock.
     * @param property The attribute of the Stock that needs to be modified (Note: for now only 1).
     * @param newValue  The new value of the property we want to edit.
     * @return the stock before edits, for printing purposes.
     */
    public Stock setStock(String stockCode, StockProperty property, String newValue)
            throws BadInputException {
        Stock updatedStock;

        //Error: StockCode not found.
        if (!isExistingStockCode(stockCode)) {
            throw new BadInputException(nonexistentStockCodeOutput(stockCode));
        }

        //Error: New StockCode is already used.
        if (property == StockProperty.STOCKCODE && isExistingStockCode(newValue)) {
            throw new BadInputException(repeatedStockCodeOutput(newValue));
        }

        for (StockType stockType : stockList) {
            updatedStock = stockType.setStock(stockCode, property, newValue);
            if (updatedStock != null) { //The corresponding stockCode was found in the StockList
                return updatedStock;
            }
        }
        return null;
    }

    /**
     * Edits a StockType object in a StockList. Note, the only edit to StockType in this version is to its name.
     * @param stockTypeName The unique String that identifies a StockType.
     * @param newName The newName of the StockType.
     * @return the stockType before editing, for printing purpose.
     */
    public StockType setStockType(String stockTypeName, String newName) {
        StockType previous;
        for (StockType stockType : stockList) {
            if (stockTypeName.equals(stockType.getName())) {
                previous = stockType;
                stockType.setName(newName);
                return previous;
            }
        }
        return null;
    }

    /**
     * Gets the total number of stocks in this stockList. This sums the number of stocks across stockTypes.
     * @return the total number of stocks.
     */
    public int getStockQuantity() { //The number of stocks in the list, across all stockTypes.
        int total = 0;
        for (StockType stockType : stockList) {
            total += stockType.getQuantity();
        }

        return total;
    }

    //@@author cyanoei
    /**
     * Determines if any of the stocks in this stockList have the same stockCode.
     * @param stockCode the queried stockCode.
     * @return true if a stock in this stockList has that stockCode and false if none of the stocks have this stockCode.
     */
    public boolean isExistingStockCode(String stockCode) {
        for (StockType stockType : stockList) {
            if (stockType.isExistingStockCode(stockCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the queried stockType already exists in the system.
     * @param stockTypeName the new name for a stockType that the user wants to add/edit.
     * @return true if the stockType is already implemented, false if it is new.
     */
    public boolean isExistingStockType(String stockTypeName) {
        for (StockType stockType : stockList) {
            if (stockType.getName().equals(stockTypeName)) {
                return true;
            }
        }
        return false;
    }


    //@@author
    /**
     * Prints every stock within stocklist whose stocktype matches query. Should only be called by Cli.
     * @return The string of the stocklist whose stocktype matches query.
     */
    public String queryStocks(String query) {
        StringBuilder ret = new StringBuilder();
        boolean found = false;
        for (StockType stocktype : stockList) {
            if (stocktype.getName().equals(query)) {
                if (!found) {
                    ret.append(query).append(" INVENTORY\n");
                    ret.append("------------------------\n");
                    found = true;
                }
                ret.append(stocktype.toString()).append("\n");
            }
        }
        return ret.toString();
    }

    /**
     * Prints all the stocktypes that are currently handled by Eggventory. Should only be called by Cli.
     * @return The string of all the stocktypes
     */
    public String toStocktypeString() {
        StringBuilder ret = new StringBuilder();
        ret.append("LISTING STOCKTYPES\n");
        for (StockType stocktype : stockList) {
            ret.append("------------------------\n");
            ret.append(stocktype.getName()).append("\n");
        }
        return ret.toString();
    }

    /**
     * Prints every stock within stocklist. Should only be called by Cli.
     * @return The string of the stocklist.
     */
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("CURRENT INVENTORY\n");

        for (StockType stocktype : stockList) {
            if (stocktype.toString() != "") { //Does not print empty StockTypes.
                ret.append("------------------------\n");
                ret.append(stocktype.toString()).append("\n");
            }
        }

        return ret.toString();
    }

    public boolean isStockTypeEmpty(StockType stocktype) {
        return (stocktype.getQuantity() == 0);
    }


    /**
     * Saves the list into a String.
     * @return The String that will be directly saved into file.
     */
    public String saveDetailsString() {
        StringBuilder details = new StringBuilder();
        for (StockType stocktype : stockList) {
            if (isStockTypeEmpty(stocktype) == false) {
                details.append(stocktype.saveDetailsString()); //Don't need to add newline.
            }
        }

        return details.toString();
    }

    //@@author patwaririshab
    /**
     * Saves the stocktypes into a String.
     * @return The String will be directly saved into a saved_stocktypes file.
     */
    public String saveStockTypesString() {
        StringBuilder stockTypesString = new StringBuilder();

        for (StockType stocktype : stockList) {
            stockTypesString.append(stocktype.getName()).append("\n");
        }
        System.out.println(stockTypesString.toString());

        return stockTypesString.toString();
    }

    //@@author Raghav-B
    /**
     * Returns TableStruct containing data on all stocks contained by StockList. This
     * TableStruct is read by the GUI table.
     * @return TableStruct with data.
     */
    public TableStruct getAllStocksStruct() {
        TableStruct tableStruct = new TableStruct("Stock List");
        tableStruct.setTableColumns("Stock Type", "Stock Code", "Quantity", "Description");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (StockType stockType : stockList) {
            dataArray.addAll(stockType.getDataAsArray());
        }
        tableStruct.setTableData(dataArray);

        return tableStruct;
    }

    /**
     * Returns TableStruct containing data on all stocktypes contained by StockList. This
     * TableStruct is read by the GUI table.
     * @return TableStruct with data.
     */
    public TableStruct getAllStockTypesStruct() {
        TableStruct tableStruct = new TableStruct("Stocktype List");
        tableStruct.setTableColumns("Stock Type");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (StockType stockType : stockList) {
            dataArray.add(new ArrayList<>(Collections.singletonList(stockType.getName())));
        }
        tableStruct.setTableData(dataArray);

        return tableStruct;
    }

    /**
     * Returns TableStruct containing data on all stocks under a specific stocktype. This
     * TableStruct is read by the GUI table.
     * @param stockTypeName Name of stocktype under which all stocks will be listed.
     * @return TableStruct with data.
     */
    public TableStruct getAllStocksInStockTypeStruct(String stockTypeName) {
        TableStruct tableStruct = new TableStruct("Stock List: " + stockTypeName);
        tableStruct.setTableColumns("Stock Type", "Stock Code", "Quantity", "Description");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (StockType stockType : stockList) {
            if (stockType.getName().equals(stockTypeName)) {
                dataArray.addAll(stockType.getDataAsArray());
            }
        }
        tableStruct.setTableData(dataArray);

        return tableStruct;
    }
    //@@author
}
