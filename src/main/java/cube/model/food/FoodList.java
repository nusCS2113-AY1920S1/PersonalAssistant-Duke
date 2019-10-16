package cube.model.food;

import java.util.ArrayList;
import cube.model.food.Food;

// for testing only
public class FoodList {
	private ArrayList<Food> foodArrayList;

	public enum SortType {
		EXPIRY,
	}

	public FoodList() {
		this.foodArrayList = new ArrayList<>();
	}

	public FoodList(ArrayList<Food> list) {
		this.foodArrayList = list;
	}

	public void add(Food food) {
		this.foodArrayList.add(food);
	}

	// public getter to allow JSON read/write to work
	public ArrayList<Food> getFoodList() {
		return this.foodArrayList;
	}

	public Food get(int index) {
		return foodArrayList.get(index);
	}

	public Food get(String foodName) {
		return new Food(foodName);
	}

	public void remove(int index) {
		foodArrayList.remove(index);
	}

	public boolean exists(Food food) {
		return foodArrayList.contains(food);
	}

	public int size() {
		return foodArrayList.size();
	}

	public void sort(SortType sortType) {
		
	}

	//public Iteator iterator() {}

}