//@@author matthewng1996

package wallet.model.currency;

public class Currency {
    private String country;
    private double value;
    private boolean currentCurrency;

    /**
     * Constructs the Currency object.
     * @param country which country the currency belongs to.
     * @param value converted value from SGD.
     */
    public Currency(String country, double value, boolean currentCurrency) {
        this.country = country;
        this.value = value;
        this.currentCurrency = currentCurrency;
    }

    /**
     * Returns the country of the currency.
     *
     * @return The country of currency.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the conversion value of the currency based on SGD.
     *
     * @return The conversion value of the currency
     */
    public double getValue() {
        return value;
    }

    /**
     * Gets a boolean based on currenct currency.
     *
     * @return Whether currency is currently used.
     */
    public boolean isCurrentCurrency() {
        return currentCurrency;
    }

    /**
     * Sets a boolean based on currenct currency.
     *
     * @param currentCurrency The currently used currency.
     */
    public void setCurrentCurrency(boolean currentCurrency) {
        this.currentCurrency = currentCurrency;
    }

}
