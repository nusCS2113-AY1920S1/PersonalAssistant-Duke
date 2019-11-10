//@@author LL-Pengfei
/**
 * RevenueStorage.java
 * Support revenue-related methods for storage.
 */
package cube.storage;

/**
 * This class supports revenue-related methods for storage.
 */
public abstract class ProfitStorage {
    private static double annualRevenue = 0;
    private static double annualProfit = 0;

    public static double getAnnualRevenue() {
        return annualRevenue;
    }

    public static void setAnnualRevenue(double annualRevenue) {
        ProfitStorage.annualRevenue = annualRevenue;
    }

    public static double getAnnualProfit() {
        return annualProfit;
    }

    public static void setAnnualProfit(double annualProfit) {
        ProfitStorage.annualProfit = annualProfit;
    }
}