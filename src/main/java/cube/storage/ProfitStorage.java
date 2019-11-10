//@@author LL-Pengfei
/**
 * ProfitStorage.java
 * Support profit and revenue generation-related methods for storage.
 */
package cube.storage;

/**
 * This class supports profit and revenue generation-related methods for storage.
 */
public abstract class ProfitStorage {
    private static double annualRevenue = 0;
    private static double annualProfit = 0;

    /**
     * Getter for Annual Revenue.
     *
     * @return The Annual Revenue.
     */
    public static double getAnnualRevenue() {
        return annualRevenue;
    }

    /**
     * Setter for Annual Revenue.
     *
     * @param annualRevenue The Annual Revenue to be set.
     */
    public static void setAnnualRevenue(double annualRevenue) {
        ProfitStorage.annualRevenue = annualRevenue;
    }

    /**
     * Getter for Annual Profit.
     *
     * @return The Annual Profit.
     */
    public static double getAnnualProfit() {
        return annualProfit;
    }

    /**
     * Setter for Annual Profit.
     *
     * @param annualProfit The Annual Profit to be set.
     */
    public static void setAnnualProfit(double annualProfit) {
        ProfitStorage.annualProfit = annualProfit;
    }
}