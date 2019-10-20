package cube.storage;

public class RevenueStorage {
    private double revenue;

    public RevenueStorage() {
        this.revenue = 0;
    }

	public RevenueStorage(double revenue) {
        this.revenue = revenue;
	}

    public double getRevenue() {
        return revenue;
    }

    public void storeRevenue(double revenue) {
        this.revenue = revenue;
    }
}