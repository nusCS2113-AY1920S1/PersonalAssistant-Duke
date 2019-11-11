//@@author matthewng1996

package wallet.model.currency;

import java.util.ArrayList;

public class CurrencyList {
    private ArrayList<Currency> currencyList;
    private Currency currentCurrency;

    public CurrencyList() {
        this.currencyList = new ArrayList<>();
    }

    /**
     * Constructs a new currencyList object.
     *
     * @param currencyArrayList The list of currencies to be added.
     */
    public CurrencyList(ArrayList<Currency> currencyArrayList) {
        this.currencyList = currencyArrayList;
        for (Currency c : currencyArrayList) {
            if (c.isCurrentCurrency()) {
                currentCurrency = c;
            }
        }
    }

    /**
     * Returns the list of currencies in the currencyList.
     *
     * @return The list of currencies.
     */
    public ArrayList<Currency> getCurrencyList() {
        return currencyList;
    }

    /**
     * Returns a Currency object.
     * @return current currency
     */
    public Currency getCurrentCurrency() {
        return currentCurrency;
    }

    /**
     * Returns a Currency object.
     * @param currentCurrency The current currency
     * @return current currency
     */
    public Currency setCurrentCurrency(Currency currentCurrency) {
        return this.currentCurrency = currentCurrency;
    }


}
