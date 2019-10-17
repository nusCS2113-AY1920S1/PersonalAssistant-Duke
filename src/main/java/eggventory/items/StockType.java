package eggventory.items;

import eggventory.enums.Property;

import java.util.ArrayList;

/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class StockType {
    private String name;
    private ArrayList<Stock> stocks;
    //private int quantity;
    //private boolean isUniqueStock;

    /**
     * Creates a new StockType object. This overload should only be called from a Storage class.
     * @param name A unique name identifying the StockType.
     * @param savedFile A fully constructed ArrayList of Stock objects.
     */
    public StockType(String name, ArrayList<Stock> savedFile) {
        this.name = name;
        stocks = savedFile;
        //quantity = savedFile.size();
        //isUniqueStock = isUniqueStock();
    }

    /**
     * Creates a new StockType object. StockType should only be instantiated from a StockList class.
     * @param name A unique name identifying the StockType.
     * @param isUniqueStock true if the Stock objects are a UniqueStock.
     */
    public StockType(String name, boolean isUniqueStock) {
        this.name = name;
        this.stocks = new ArrayList<>();
        //this.quantity = 0;
        //this.isUniqueStock = isUniqueStock;
    }

    /**
     * Creates a new StockType object. StockType should only be instantiated from a StockList class.
     * @param name A unique name identifying the StockType.
     */
    public StockType(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
        //this.quantity = 0;
        //this.isUniqueStock = false;
    }

    /**
     * Determines whether this StockType is a collection of UniqueStock or CollectiveStock.
     */
    /*
        private boolean isUniqueStock() {
            for (Stock stock : stocks) {
                if (stock instanceof CollectiveStock) {
                    return false;
                }
            }
            return true;
        }
    */

    /**
     * Adds a stock to the stockList.
     * @return True if item was added successfully.
     */
    public boolean addStock(String stockType, String stockCode, int quantity, String description) {
        stocks.add(new CollectiveStock(stockType, stockCode, quantity, description));
        //this.quantity++;
        return true;
    }

    /**
     * Deletes a stock of the user's choice.
     *
     * @param stockCode The code of the stock to be deleted.
     * @return true if some stockCode was found and the corresponding stock removed. false if none were found.
     */
    public Stock deleteStock(String stockCode) {

        //While this is nice, we are unable to print a confirmation message of the stock deleted.
        //stocks.removeIf(stock -> stock.getStockCode().equals(stockCode));

        Stock deletedStock;

        for (Stock stock : stocks) {
            if (stockCode.equals(stock.getStockCode())) {
                deletedStock = stock; //Not sure if this is a copy or not. Assumes unique stockCodes.
                stocks.remove(stock);
                return deletedStock;
            }
        }
        return null;
        //this.quantity--;
    }

    /**
     * Accesses and sets a new stockCode of a stock.
     * @param oldStockCode The current stockcode referring to this stock.
     * @param newStockCode The new stockcode to change to.
     */
    public void setStockCode(String oldStockCode, String newStockCode) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(oldStockCode)) {
                stock.setStockCode(newStockCode);
                return;
            }
        }
    }

    /**
     * Accesses and sets the quantity of a Stock.
     * @param stockCode StockCode of the stock to change
     * @param quantity New quantity of the stock to change
     */
    public void setStockQuantity(String stockCode, int quantity) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setQuantity(quantity);
                return;
            }
        }
    }

    /**
     * Accesses and sets the description of a Stock.
     * @param stockCode StockCode of the stock to change
     * @param description New description of the stock to change
     */
    public void setStockDescription(String stockCode, String description) {
        for (Stock stock : stocks) {
            if (stock.getStockCode().equals(stockCode)) {
                stock.setDescription(description);
                return;
            }
        }
    }

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

    /**
     * Updates the name of the StockType.
     * @param newName String which uniquely identifies a StockType.
     * @return The name of the StockType, for printing purpose.
     */
    public String setName(String newName) {
        this.name = newName;
        return this.name;
    }

    /**
     * Updates the values of properties of a Stock.
     * @param stockCode String which uniquely identifies a Stock.
     * @param property The attribute of a Stock we want to update.
     * @param newValue The new value of the attribute to be updated.
     * @return The unedited Stock, for printing purpose.
     */
    public Stock setStock(String stockCode, Property property, String newValue) {
        Stock uneditedStock;
        for (Stock stock: stocks) {
            if (stockCode.equals(stock.getStockCode())) {
                uneditedStock = stock;
                switch (property) {
                case STOCKCODE:
                    stock.setStockCode(newValue);
                    break;
                case QUANTITY:
                    stock.setQuantity(Integer.parseInt(newValue));
                    break;
                case LOANED:
                    stock.setLoaned(Integer.parseInt(newValue));
                    break;
                case LOST:
                    stock.setLost(Integer.parseInt(newValue));
                    break;
                case DESCRIPTION:
                    stock.setDescription(newValue);
                    break;
                case MINIMUM:
                    stock.setMinimum(Integer.parseInt(newValue));
                    break;
                default:
                }
                return uneditedStock;
            }
        }
        return null;
    }

    /**
     * A string of all the stock objects within this stocktype. Should only be called by Cli and StockList.
     * @return A string list of all the stock objects and their details.
     */
    public String toString() {
        String ret = "";
        int i = 1;

        for (Stock stock : stocks) {
            ret += String.format("%d. ", i++) + stock.toString() + "\n";
        }

        return ret;

    }

    /**
     * Creates a String of all Stock objects under this StockType.
     * @return The String of all Stock objects.
     */
    public String saveDetailsString() {
        String details = "";
        for (Stock stock : stocks) {
            details += stock.saveDetailsString() + "\n";
        }
        return details;
    }

}
