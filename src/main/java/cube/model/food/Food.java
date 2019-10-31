package cube.model.food;

import cube.model.ModelManager;

import java.util.Date;

public class Food {

	//Identity fields
	protected String name;
	protected String type;
	protected double price;
	protected double cost;
	protected int stock;
	protected Date expiryDate;
	protected double foodRevenue;
	//Data fields
	//protected static double revenue;


    /**
     * Default constructor.
     * Calls another constructor with (null) as argument.
     */
	public Food() {
	    this(null);
    }

	/**
	 * Constructor with one argument.
	 *
	 * @param name Name of the food product.
	 */

	public Food(String name) {
		this.name = name;
		this.foodRevenue = 0;
	}

	/**
	 * Gets the name of the food product.
	 *
	 * @return the name of the food product.
	 */

	public String getName() {
		return name;
	}

	/**
	 * Sets the type of the product.
	 *
	 * @param type The type of the product.
	 */

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the type of the product.
	 *
	 * @return the type of the product.
	 */

	public String getType() {
		return type;
	}


	/**
	 * Sets/changes the price of the product.
	 *
	 * @param price The price of the product.
	 */

	public void setPrice (double price) {
		this.price = price;
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
	 * Sets/updates the expiry date of the product.
	 *
	 * @param expiryDate The expiry date of the product.
	 */

	public void setExpiryDate (Date expiryDate) {
		this.expiryDate = expiryDate;
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
	 * Updates the total revenue made from selling the product.
	 *
	 * @param newRevenue New total revenue made from selling the product.
	 */
	/*
	public static void updateRevenue(double newRevenue) {
			revenue = newRevenue;
	}
    */

	/**
	 * Generates the total revenue earned from selling the product.
	 *
	 * @return the total revenue earned from selling the product.
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

	public double getFoodRevenue() {
		return foodRevenue;
	}

	public void setFoodRevenue(double revenue) {
		this.foodRevenue = revenue;
	}


	/**
	 * Updates the quantity of the product available in stock.
	 *
	 * @param newStock New quantity of product available.
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