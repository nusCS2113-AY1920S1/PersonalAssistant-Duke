package cube.storage;

import cube.model.food.Food;
import cube.model.food.FoodList;

import java.util.ArrayList;

public class FoodStorage {
	private ArrayList<Food> foodArrayList;

	public FoodStorage() {
		this.foodArrayList = new ArrayList<>();
	}

	public FoodStorage(ArrayList<Food> foodArrayList) {
		this.foodArrayList = foodArrayList;
	}

	public ArrayList<Food> loadFood() {
		return null;
	}

	public void appendFood(Food food) {
		foodArrayList.add(food);
	}

	public void storeFoodList(FoodList foodlist) {

	}
}