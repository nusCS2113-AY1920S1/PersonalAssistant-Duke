package cube.storage;

// for testing only
public class RevenueStorage {
    private double revenue;

    public RevenueStorage() {
        this.revenue = 0;
    }

	public RevenueStorage(double revenue) {
        this.revenue = revenue;
	}

    public double loadRevenue() {
        return revenue;
    }

    public void storeRevenue(double revenue) {
        this.revenue = revenue;
    }
}