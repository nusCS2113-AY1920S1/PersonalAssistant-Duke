package cube.storage.config;

public class ReminderConfig {
    private int daysToExpiry;
    private int stockIndex;

    /**
     * Default constructor.
     * Creates a new instance of ConfigStorage class with default settings.
     */
    public ReminderConfig() {
        this.daysToExpiry = 7;
        this.stockIndex = 5;
    }

    /**
     * Constructor that takes in 2 arguments.
     * Creates a new instance of ConfigStorage class with supplied settings.
     */
    public ReminderConfig(int daysToExpiry, int stockIndex) {
        this.daysToExpiry = daysToExpiry;
        this.stockIndex = stockIndex;
    }

    public int getDaysToExpiry() {
        return daysToExpiry;
    }

    public void setDaysToExpiry(int daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }

    public int getStockIndex() {
        return stockIndex;
    }

    public void setStockIndex(int stockIndex) {
        this.stockIndex = stockIndex;
    }
}
