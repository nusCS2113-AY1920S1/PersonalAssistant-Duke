//@@author LL-Pengfei
/**
 * Food.java
 * Model the food object.
 */
package cube.model.food;

import cube.model.ModelManager;
import java.util.Date;

public class Food {

	protected String name;
	protected String type;
	protected double price;
	protected double cost;
	protected int stock;
	protected Date expiryDate;
	protected double foodRevenue;

    /**
     * The default constructor.
     * Call the other constructor (overloading constructor) with (null) as argument.
     */
	public Food() {
	    this(null);
    }

	/**
	 * The constructor with the name of the food object as the argument.
	 *
	 * @param name Name of the food object.
	 */
	public Food(String name) {
		this.name = name;
		this.foodRevenue = 0;
	}

	/**
	 * Getter for the name of the food.
	 *
	 * @return the name of the food.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the type of the food.
	 *
	 * @param type The type of the food to be set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter for the type of the food.
	 *
	 * @return the type of the food.
	 */
	public String getType() {
		return type;
	}


	/**
	 * Setter for the price of the food.
	 *
	 * @param price The price of the food to be set.
	 */
	public void setPrice (double price) {
		this.price = price;
	}

	/**
	 * Getter for the price of the food.
	 *
	 * @return the price of the food.
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * Setter for the expiry date of the food.
	 *
	 * @param expiryDate The expiry date of the food to be set.
	 */
	public void setExpiryDate (Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Getter for the expiry date of the food.
	 *
	 * @return the expiry date of the food.
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Getter for the total revenue earned from selling the food.
	 *
	 * @return the total revenue earned from selling the food.
	 */
	public static double getRevenue() {
		FoodList list = ModelManager.getFoodList();
		int size = list.size();
		double revenue = 0;
		for (int i = 0; i < size; ++i) {
			revenue += list.get(i).foodRevenue;
		}
		return revenue;
	}

	/**
	 * Getter for the revenue of a food.
	 *
	 * @return the revenue of a food.
	 */
	public double getFoodRevenue() {
		return foodRevenue;
	}

	/**
	 *
	 * @param revenue
	 */
	public void setFoodRevenue(double revenue) {
		this.foodRevenue = revenue;
	}


	/**
	 * Setter for the quantity of the food available in stock.
	 *
	 * @param newStock The new quantity of the food available to be set.
	 */
	public void setStock(int newStock) {
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

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getCost() {
		return cost;
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

	/**
	 * Casts the food product to String type.
	 *
	 * @return the String printout of the food product.
	 */
	@Override
	public String toString() {
		return name + "\n  Type: " + type +
				"\n  Price: $" + price +
				"\n  Stock: " + stock +
				"\n  Expiry Date: " + expiryDate;
	}

	@Override
	// for Junit test use
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (other instanceof Food) {
			Food b = (Food) other;
			return name.equals(b.name)
					&& ((type == null && b.type == null) || (type != null && type.equals(b.type)))
					&& price == b.price
					&& cost == b.cost
					&& stock == b.stock
					&& expiryDate == b.expiryDate;
		} else {
			return false;
		}
	}

}