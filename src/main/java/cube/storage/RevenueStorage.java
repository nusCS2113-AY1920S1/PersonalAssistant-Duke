package cube.storage;

import java.io.Serializable;

// for testing only
public class RevenueStorage implements Serializable {
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