/**
 * A sale object represents a transaction.
 *
 * @author tygq13
 */
package cube.model.sale;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sale implements Comparable<Sale>{
	@JsonProperty
	protected int quantitySold;
	@JsonProperty
	protected double profit;
	@JsonProperty
	protected double revenue;
	@JsonProperty
	protected Date soldDate;

	public Sale(){
		this(0, 0, 0, null);
	}

	/**
	 * Constructor with four arguments.
	 *
	 * @param quantitySold The quantitySold sold in this sale.
	 * @param profit The profit earned in this sale.
	 * @param revenue The revenue earned in this sale.
	 * @param soldDate The date of sale.
	 */
	public Sale(int quantitySold, double revenue, double profit, Date soldDate) {
		this.quantitySold = quantitySold;
		this.revenue = revenue;
		this.profit = profit;
		this.soldDate = soldDate;
	}

	@JsonIgnore
	public int getQuantity() {
		return quantitySold;
	}

	@JsonIgnore
	public double getRevenue() {
		return revenue;
	}

	@JsonIgnore
	public double getProfit() {
		return profit;
	}

	@JsonIgnore
	public Date getDate() {
		return soldDate;
	}

	@Override
	public String toString() {
		return "  Quantity Sold: " + quantitySold +
				"\n  Revenue: $" + revenue +
				"\n  Profit: " + profit +
				"\n  Transaction Date: " + soldDate;
	}

	@Override
	public int compareTo(Sale b) {
		return this.soldDate.compareTo(b.getDate());
	}
}