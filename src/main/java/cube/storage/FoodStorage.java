/**
 * Support food-related methods for storage.
 *
 * @author tygq13
 */

package cube.storage;

import cube.model.food.FoodList;

public class FoodStorage {
	private FoodList foodList;

	/**
	 * Default constructor for FoodStorage.
	 */
	public FoodStorage() {
		this.foodList = new FoodList();
	}

	/**
	 * Getter for FoodList.
	 * @return FoodList containing list of Food objects.
	 */
	public FoodList getFoodList() {
		return foodList;
	}

	/**
	 * Setter for FoodList.
	 * @param foodlist Object containing the list of Food objects.
	 */
	public void storeFoodList(FoodList foodlist) {
		this.foodList = foodlist;
	}
}