/**
 * The facade of all the model list, so that model can be accessed through a single api.
 *
 * @author tygq13
 */
package cube.model;

import cube.model.food.FoodList;
import cube.model.promotion.PromotionList;
import cube.model.sale.SalesHistory;

public class ModelManager {
	private static FoodList foodList;
	private static SalesHistory salesHistory;
	private PromotionList promotionList;

	/**
	 * Default constructor.
	 * Creates new instances of foodList, salesHistory and promotionList.
	 */
	public ModelManager() {
		foodList = new FoodList();
		salesHistory = new SalesHistory();
		this.promotionList =  new PromotionList();
	}

	/**
	 * Constructor with three arguments
	 * @param foodList The food list to be managed.
	 * @param salesHistory The sales history to be managed.
	 * @param promotionList The promotion list to be managed.
	 */
	public ModelManager(FoodList foodList, SalesHistory salesHistory, PromotionList promotionList) {
		ModelManager.foodList = foodList;
		ModelManager.salesHistory = salesHistory;
		this.promotionList = promotionList;
	}

	/**
	 * Gets the food list.
	 * @return The food list.
	 */
	public static FoodList getFoodList() {
		return foodList;
	}

	/**
	 * Sets the food list to be managed.
	 * @param foodList The food list to be managed.
	 */
	public void setFoodList(FoodList foodList) {
		this.foodList = foodList;
	}

	/**
	 * Gets the sales history.
	 * @return The sales history.
	 */
	public static SalesHistory getSalesHistory() {
		return salesHistory;
	}

	/**
	 * Gets the promotion list.
	 * @return The promotion list.
	 */
	public PromotionList getPromotionList() { return promotionList; }
}