//@@author LL-Pengfei
package cube.storage;

/**
 * This class supports revenue-related methods for storage.
 */
public class RevenueStorage {
    private double revenue;

    /**
     * Default constructor, setting revenue to 0.
     */
    public RevenueStorage() {
        this.revenue = 0;
    }

    /**
     * Getter for revenue.
     *
     * @return The revenue value.
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * Setter for revenue.
     *
     * @param revenue The revenue variable, kept for convenient access to the total revenue.
     */
    public void storeRevenue(double revenue) {
        this.revenue = revenue;
    }
}