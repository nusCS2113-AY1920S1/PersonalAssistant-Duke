package eggventory.logic;

import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.model.items.Stock;
import eggventory.model.items.StockType;
import eggventory.ui.TableStruct;

import java.util.ArrayList;

//@@author cyanoei
public class QuantityManager {

    /**
     * Obtains the loaned quantity of the given stock.
     * @param stock the stock in question.
     * @return the quantity of it that has been loaned.
     */
    private static int getLoanedQuantity(Stock stock) {
        String stockCode = stock.getStockCode();
        int loaned = LoanList.getStockLoanedQuantity(stockCode);
        return loaned;
    }

    /**
     * Checks of minimum required quantity is fulfilled.
     * @param stock the stock to check.
     * @return true if minimum required is met, false otherwise.
     */
    private static boolean isLessThanMinimum(Stock stock) {
        int totalQuantity = stock.getQuantity();
        int loaned = getLoanedQuantity(stock);
        int lost = stock.getLost();
        int minimum = stock.getMinimum();

        return totalQuantity - loaned - lost < minimum;
    }

    /**
     * Calculates the available stock.
     * @param stock the stock to check.
     * @return the calculated value.
     */
    private static int calculateRemaining(Stock stock) {
        int totalQuantity = stock.getQuantity();
        int loaned = getLoanedQuantity(stock);
        int lost = stock.getLost();

        return totalQuantity - loaned - lost;
    }

    private static int toBuy(Stock stock) {
        return stock.getMinimum() - calculateRemaining(stock);
    }


    /**
     * Checks if minimum required quantity of the stock is fulfilled.
     * @param stock the stock to check.
     * @return the output string if the stock has less than the minimum required, a blank string otherwise.
     */
    public static String checkMinimum(Stock stock) {
        int minimum = stock.getMinimum();
        int newTotal = calculateRemaining(stock);

        if (isLessThanMinimum(stock)) {
            return String.format("\nThe stock \"%s\" is currently below minimum quantity. "
                    + "Available quantity: %d. Minimum quantity: %d.", stock.getDescription(), newTotal, minimum);
        } else {
            return ""; //No need for a warning message if quantity is ok.
        }

    }

    /**
     * Creates a list of all stocks that are below their minimum required quantity.
     * @param list the StockList.
     * @return an ArrayList containing stocks below minimum quantity.
     */
    private static ArrayList<Stock> lessThanMinimumList(StockList list) {
        ArrayList<Stock> minimumList = new ArrayList<>();

        for (StockType stockType : list.getList()) {
            for (Stock stock : stockType.getStockList()) {
                if (isLessThanMinimum(stock)) {
                    minimumList.add(stock);
                }
            }
        }
        return minimumList;
    }

    /**
     * Public access to lessThanMinimumList method.
     * @param list the StockList.
     * @return
     */
    public ArrayList<Stock> getLessThanMinimumList(StockList list) {
        return lessThanMinimumList(list);
    }

    /**
     * Augments the Stock's toString to show meaningful info about the minimum quantity in CLI.
     * @param stock the Stock.
     * @param loaned the amount loaned.
     * @return the formatted String.
     */
    private static String formatStock(Stock stock, int loaned) {
        return stock.toString() + " | Loaned: " + Integer.toString(loaned) + " | Minimum: " + stock.getMinimum();
    }

    /**
     * Creates a stock description string stating the quantity to buy to reach the minimum quantity.
     * @param stock the Stock.
     * @param loaned the amount loaned.
     * @return the formatted shopping String.
     */
    private static String formatStockToBuy(Stock stock, int loaned) {
        int toBuy = toBuy(stock);
        return stock.getStockCode() + ": " + stock.getDescription() + " | To buy: " + Integer.toString(toBuy);
    }

    /**
     * Produces output string describing stocks that are below the minimum required quantity.
     * @param minimumList The ArrayList of stocks which are below minimum required quantity.
     * @return the output string.
     */
    private static String lessThanMinimumOutput(ArrayList<Stock> minimumList) {
        String output = "";

        if (minimumList.size() == 0) {
            output = "No stocks are below their minimum quantity! All's good!";
        } else {
            output += "These stocks have less than the minimum quantity you wanted to keep:\n";
            int i = 1;
            for (Stock stock : minimumList) {
                int loaned = getLoanedQuantity(stock);
                output += i++ + ". " + formatStock(stock, loaned) + "\n";
            }
        }
        return output;
    }

    /**
     * Returns the table required for GUI to show the list of stocks below minimum quantity.
     * @param minimumList the ArrayList of stocks.
     * @return the tableStruct.
     */
    private static TableStruct getLessThanMinimumStocksStruct(ArrayList<Stock> minimumList) {
        TableStruct tableStruct = new TableStruct("List of Stocks below Minimum Quantity");
        tableStruct.setTableColumns("Stock Type", "Stock Code", "Total Quantity", "Description", "Minimum", "Loaned");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (Stock stock : minimumList) {
            ArrayList<String> stockDescription = stock.getDataAsArray();
            dataArray.add(stockDescription);
        }
        tableStruct.setTableData(dataArray);

        return tableStruct;
    }

    /**
     * Formats the output for shopping list.
     * @param minimumList the ArrayList of below minimum stocks.
     * @return the shopping list.
     */
    private static String shoppingListOutput(ArrayList<Stock> minimumList) {
        String output = "";

        if (minimumList.size() == 0) {
            output = "Nothing to buy - no stocks are below their minimum quantity!";
        } else {
            output += "Here's a list of things you might want to stock up on:\n";
            int i = 1;
            for (Stock stock : minimumList) {
                int loaned = getLoanedQuantity(stock);
                output += Integer.toString(i++) + ". " + formatStockToBuy(stock, loaned) + "\n";
            }
        }
        return output;
    }

    /**
     * Returns the table required for GUI to show the shopping list.
     * @param minimumList the ArrayList of stocks below minimum.
     * @return the tableStruct.
     */
    private static TableStruct shoppingListTable(ArrayList<Stock> minimumList) {
        TableStruct tableStruct = new TableStruct("Recommended Shopping List:");
        tableStruct.setTableColumns("Stock Type", "Stock Code", "Description", "Quantity To Buy");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (Stock stock : minimumList) {
            ArrayList<String> stockArray = new ArrayList<>();

            stockArray.add(stock.getStockType());
            stockArray.add(stock.getStockCode());
            stockArray.add(stock.getDescription());
            stockArray.add(Integer.toString(toBuy(stock)));

            dataArray.add(stockArray);
        }
        tableStruct.setTableData(dataArray);

        return tableStruct;
    }

    /**
     * Public method for printing the listMinimum output.
     * @param list the StockList.
     * @return the print output.
     */
    public static String printMinimumOutput(StockList list) {
        return lessThanMinimumOutput(lessThanMinimumList(list));
    }

    /**
     * Public method for returning the TableStruct for GUI display.
     * @param list the StockList.
     * @return the TableStruct.
     */
    public static TableStruct printMinimumTable(StockList list) {
        return getLessThanMinimumStocksStruct(lessThanMinimumList(list));
    }

    /**
     * Public method for printing the listMinimum output.
     * @param list the StockList.
     * @return the print output.
     */
    public static String printShoppingList(StockList list) {
        return shoppingListOutput(lessThanMinimumList(list));
    }

    /**
     * Public method for returning the TableStruct for GUI display.
     * @param list the StockList.
     * @return the TableStruct.
     */
    public static TableStruct printShoppingListTable(StockList list) {
        return shoppingListTable(lessThanMinimumList(list));
    }
}
