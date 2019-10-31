package cube.model;

import cube.model.food.FoodList;
import cube.model.sale.SalesHistory;

public class ModelManager {
	private static FoodList foodList;
	private SalesHistory salesHistory;

	/**
	 * Default constructor.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public ModelManager() {
		this.foodList = new FoodList();
		this.salesHistory = new SalesHistory();
	}

	/**
	 * Constructor with 2 arguments.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public ModelManager(FoodList foodList, SalesHistory salesHistory) {
		this.foodList = foodList;
		this.salesHistory = salesHistory;
	}

	public static FoodList getFoodList() {
		return foodList;
	}

	public void setFoodList(FoodList foodList) {
		this.foodList = foodList;
	}

	public SalesHistory getSalesHistory() {
		return salesHistory;
	}

}