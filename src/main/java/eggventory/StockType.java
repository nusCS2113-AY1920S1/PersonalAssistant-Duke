package eggventory;

import java.util.ArrayList;
import eggventory.exceptions.BadInputException;
import eggventory.items.Stock;

/**
 * Manages the list of (different types of classes),
 * including all the methods to modify the list:
 * Adding each of the 3 types, print, delete, mark as done, search.
 */

public class StockType {
    private ArrayList<Stock> stockList;

    public StockType(ArrayList<Stock> savedFile) {
        stockList = savedFile;
    }

    public StockType() {
        stockList = new ArrayList<>();
    }

    /**
     * Returns the entire stockList.
     * @return the stockList.
     */
    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    /**
     * Gets the number of stocks in the stockList.
     * @return the number of stocks in the stockList.
     */
    public int getSize() {
        return stockList.size();
    }

    /**
     * Adds a stock to the stockList.
     * @return True if item was added successfully.
     */
    public boolean addStock(String stockType, String stockCode, int quantity, String description) {
        stockList.add(new Stock(stockType, stockCode, quantity, description));
        return true;
    }

    /**
     * Returns a stock of the user's choice.
     *
     * @param i the index of the stock selected.
     */
    public Stock getStock(int i) {
        return stockList.get(i);
    }

    /**
     * Deletes a stock of the user's choice.
     *
     * @param i the index of the task to be deleted.
     */
    public void deleteStock(int i) {
        stockList.remove(i);
    }

    /**
     * Looks for undone deadlines within the next 5 Days and prints the task.
     */
    /*
    private void printReminders() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        long millisInFiveDays = 5 * 24 * 60 * 60 * 1000;

        for (Stock stock : stockList) {
            if (stock instanceof Deadline && !stock.getIsDone()) {
                Deadline deadline = (Deadline) stock;
                long timeDifference = deadline.getDate().getTime().getTime() - now.getTime();
                if (timeDifference <= millisInFiveDays && timeDifference > 0) {
                    stock.printTaskDetails();
                }
            }
        }
    }
    */
}
