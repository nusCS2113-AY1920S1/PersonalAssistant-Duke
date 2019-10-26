/**
 * A sale object represents a transaction.
 *
 * @author tygq13
 */
package cube.model.sale;

import java.util.Date;

public class Sale {

	protected int quantitySold;
	protected double profit;
	protected double revenue;
	protected Date soldDate;

	/**
	 * Constructor with four arguments.
	 *
	 * @param quantity The quantity sold in this sale.
	 * @param profit The profit earned in this sale.
	 * @param revenue The revenue earned in this sale.
	 * @param date The date of sale.
	 */
	public Sale(int quantity, double revenue, double profit, Date date) {
		this.quantitySold = quantity;
		this.revenue = revenue;
		this.profit = profit;
		this.soldDate = date;
	}

	public int getQuantity() {
		return quantitySold;
	}

	public double getRevenue() {
		return revenue;
	}

	public double getProfit() {
		return profit;
	}

	public Date getDate() {
		return soldDate;
	}

}