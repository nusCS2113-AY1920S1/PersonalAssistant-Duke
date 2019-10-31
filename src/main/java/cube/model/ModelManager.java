package cube.model;

import cube.model.food.FoodList;
import cube.model.promotion.PromotionList;
import cube.model.sale.SalesHistory;

public class ModelManager {
	private static FoodList foodList;
	private SalesHistory salesHistory;
	private PromotionList promotionList;

	/**
	 * Default constructor.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public ModelManager() {
		this.foodList = new FoodList();
		this.salesHistory = new SalesHistory();
		this.promotionList =  new PromotionList();
	}

	/**
	 * Constructor with 2 arguments.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public ModelManager(FoodList foodList, SalesHistory salesHistory, PromotionList promotionList) {
		this.foodList = foodList;
		this.salesHistory = salesHistory;
		this.promotionList = promotionList;
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

	public PromotionList getPromotionList() { return promotionList; }
}