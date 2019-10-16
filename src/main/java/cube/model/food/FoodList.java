package cube.model.food;

import java.util.ArrayList;
import cube.model.food.Food;
import java.util.Collections;

// for testing only
public class FoodList {
	private ArrayList<Food> foodArrayList;

	public enum SortType {
		EXPIRY, NAME, STOCK
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
		for(Food food : foodArrayList) {
			if (food.getName().equals(foodName)) {
				return food;
			}
		}
		return null;
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
		switch(sortType) {
			case EXPIRY:
				Collections.sort(foodArrayList, new SortByExpiry());
				break;
			case NAME:
				Collections.sort(foodArrayList, new SortByName());
				break;
			case STOCK:
				Collections.sort(foodArrayList, new SortByStock());
		}
	}

	//public Iteator iterator() {}

}