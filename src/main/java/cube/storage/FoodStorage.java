package cube.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import cube.model.Food;
import cube.model.FoodList;

public class FoodStorage {
	@JsonProperty
	private FoodList foodList;

	public FoodStorage() {
		this.foodList = new FoodList();
	}

	public FoodStorage(FoodList foodList) {
		this.foodList = foodList;
	}

	public FoodList getFoodList() {
		return foodList;
	}

	public void appendFood(Food food) {
		foodList.add(food);
	}

	public void storeFoodList(FoodList foodlist) {
		this.foodList = foodlist;
	}
}