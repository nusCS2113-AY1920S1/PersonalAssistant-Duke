/**
 * A sale object represents a transaction. 
 * Date of sale is mandatory. If not specified, will be set to date of object constructor by default.
 *
 * @author tygq13
 */
package cube.model.sale;

import java.util.Date;

public class Sale {

	protected int quantitySold;
	protected double profit;
	protected double revenue;
	protected Date dateSold;

	/**
	 * Constructor with three argument.
	 * Call another constructor with dateSold=today's date.
	 * @param quantity The quantity sold in this sale.
	 * @param profit The profit earned in this sale.
	 * @param revenue The revenue earned in this sale.
	 */
	public Sale(int quantity, double profit, double revenue) {
		this(quantity, profit, revenue, new Date());
	}

	/**
	 * Constructor with four arguments.
	 *
	 * @param quantity The quantity sold in this sale.
	 * @param profit The profit earned in this sale.
	 * @param revenue The revenue earned in this sale.
	 * @param date The date of sale.
	 */
	public Sale(int quantity, double profit, double revenue, Date date) {
		this.quantitySold = quantity;
		this.profit = profit;
		this.revenue = revenue;
		this.dateSold = date;
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
		return dateSold;
	}

}