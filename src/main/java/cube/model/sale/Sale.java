/**
 * A sale object represents a transaction.
 *
 * Implements: Comparable
 *
 * @author tygq13
 */
package cube.model.sale;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Sale implements Comparable<Sale>{
	@JsonProperty
	protected String foodName;
	@JsonProperty
	protected int quantitySold;
	@JsonProperty
	protected double profit;
	@JsonProperty
	protected double revenue;
	@JsonProperty
	protected Date soldDate;

	public Sale(){
		this(null, 0, 0, 0, null);
	}

	/**
	 * Constructor with four arguments.
	 *
	 * @param quantitySold The quantitySold sold in this sale.
	 * @param profit The profit earned in this sale.
	 * @param revenue The revenue earned in this sale.
	 * @param soldDate The date of sale.
	 */
	public Sale(String foodName, int quantitySold, double revenue, double profit, Date soldDate) {
		this.foodName = foodName;
		this.quantitySold = quantitySold;
		this.revenue = revenue;
		this.profit = profit;
		this.soldDate = soldDate;
	}

	/**
	 * Getter for name of food sold in sale record.
	 * @return The food name.
	 */
	@JsonIgnore
	public String getName() {
		return foodName;
	}

	/**
	 * Getter for quanity of food sold in sale record.
	 * @return The food quanity.
	 */
	@JsonIgnore
	public int getQuantity() {
		return quantitySold;
	}

	/**
	 * Getter for revenue of food sold in sale record.
	 * @return The food revenue.
	 */
	@JsonIgnore
	public double getRevenue() {
		return revenue;
	}

	/**
	 * Getter for profit of food sold in sale record.
	 * @return The food profit.
	 */
	@JsonIgnore
	public double getProfit() {
		return profit;
	}

	/**
	 * Getter for date of food sold in sale record.
	 * @return The food date.
	 */
	@JsonIgnore
	public Date getDate() {
		return soldDate;
	}

	/**
	 * Converts the sale record to String.
	 * @return The String format of sale record.
	 */
	@Override
	public String toString() {
		return "  Quantity Sold: " + quantitySold +
				"\n  Revenue: $" + revenue +
				"\n  Profit: " + profit +
				"\n  Transaction Date: " + soldDate;
	}

	/**
	 * Compares with another sale record.
	 * This allows sale record to be sorted.
	 * @return -1 if smaller, 0 if equal, 1 if larger.
	 */
	@Override
	public int compareTo(Sale b) {
		// expand the list of comparison to reduce chance of equal transaction
		if(soldDate.compareTo(b.getDate()) != 0) {
			return soldDate.compareTo(b.getDate());
		} else if (foodName.compareTo(b.getName()) != 0) {
			return foodName.compareTo(b.getName());
		} else {
			// the order does not matter if time and name the same
			return 1;
		}
	}

	/**
	 * Compares with another object to check if they are identical.
	 * @param other The other object.
	 * @return true if identical, otherwise false.
	 */
	@Override
	// for Junit test use
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Sale) {
			Sale b = (Sale) other;
			return foodName.equals(b.foodName)
					&& quantitySold == b.quantitySold
					&& revenue == b.revenue
					&& profit == b.profit
					&& soldDate == b.soldDate;
		} else {
			return false;
		}
	}
}