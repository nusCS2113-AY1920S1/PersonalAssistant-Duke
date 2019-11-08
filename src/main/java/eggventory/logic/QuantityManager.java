package eggventory.logic;

import eggventory.model.LoanList;
import eggventory.model.items.Stock;

//@@author cyanoei
public class QuantityManager {

    /**
     * Checks of minimum required quantity is fulfilled.
     * @param totalQuantity total quantity of the stock.
     * @param loaned amount of stock loaned out.
     * @param lost amount of stock lost.
     * @param minimum minimum required quantity of stock.
     * @return true if minimum required is met, false otherwise.
     */
    private static boolean isMinimumFulfilled(int totalQuantity, int loaned, int lost, int minimum) {
        return totalQuantity - loaned - lost >= minimum;
    }

    /**
     * Calculates the available stock.
     * @param totalQuantity the total quantity of stock.
     * @param loaned the amount of the stock loaned out.
     * @param lost the amount of the stock that has been lost.
     * @return the calculated value.
     */
    private static int calculateRemaining(int totalQuantity, int loaned, int lost) {
        return totalQuantity - loaned - lost;
    }

    /**
     * Checks if minimum required quantity of the stock is fulfilled.
     * @param stock the stock to check.
     * @return the output string if the stock has less than the minimum required, a blank string otherwise.
     */
    public static String checkMinimum(Stock stock) {
        int totalQuantity = stock.getQuantity();
        int loaned = LoanList.getStockLoanedQuantity(stock.getStockCode());
        int lost = stock.getLost();
        int minimum = stock.getMinimum();

        int newTotal = calculateRemaining(totalQuantity, loaned, lost);

        if (!isMinimumFulfilled(totalQuantity, loaned, lost, minimum)) {
            return String.format("\nThe stock \"%s\" is currently below minimum quantity. "
                    + "Available quantity: %d. Minimum quantity: %d.", stock.getDescription(), newTotal, minimum);
        } else {
            return ""; //No need for a warning message if quantity is ok.
        }

    }

}
