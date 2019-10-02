package cube.model.food;

import java.io.Serializable;
import java.util.Date;

public class Food implements Serializable {
	protected String name;
	protected String type;
	protected static double revenue;
	protected double price;
	protected int stock;
	protected Date expiryDate;

	/**
	 * Constructor with four arguments.
	 *
	 * @param name Name of the food product.
	 * @param type Type of food product.
	 * @param price Price of the product.
	 * @param expiryDate Date on which the product expires.
	 */

	public Food(String name, String type, double price, Date expiryDate) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.expiryDate = expiryDate;
		this.revenue = 0;
		this.stock = 0;
	}

	/**
	 * Function called when price of the product needs to be changed.
	 *
	 * @param newPrice New price of the product.
	 */

	public void updatePrice (double newPrice) {
		price = newPrice;
	}

	/**
	 * Gets the price of the product.
	 *
	 * @return the price of the product.
	 */

	public double getPrice() {
		return price;
	}


	/**
	 * Gets the expiry date of the product.
	 *
	 * @return the expiry date of the product.
	 */

	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Updates the expiry date of the product.
	 *
	 * @param newExpiryDate New expiry date of the product.
	 */

	public void updateExpiryDate (Date newExpiryDate) {
		expiryDate = newExpiryDate;
	}


	/**
	 * Updates the total revenue made from selling the product.
	 *
	 * @param newRevenue New total revenue made from selling the product.
	 */

	public static void updateRevenue(double newRevenue) {
		revenue = newRevenue;
	}

	/**
	 * Gets the revenue earned from selling the product.
	 *
	 * @return the revenue earned from selling the product.
	 */

	public static double getRevenue() {
		return revenue;
	}


	/**
	 * Updates the quantity of the product available in stock.
	 *
	 * @param newStock New quantity of product available.
	 */
	public void updateStock(int newStock) {
		stock = newStock;
	}

	/**
	 * Gets the quantity of the product available in stock.
	 *
	 * @return Quantity of product in stock.
	 */

	public int getStock() {
		return stock;
	}

	/**
	 * Shows that product already exists in the inventory.
	 *
	 * @return true
	 */


	public boolean exists(String foodName) {
		if (foodName == this.name) {
			return true;
		}
		return false;
	}

	public String toString() {
		return name + "\n  Type: " + type +
				"\n  Price: " + price +
				"\n  Stock: " + stock +
				"\n  Expiry Date: " + expiryDate +
				"\n  Revenue: " + revenue;

	}
}