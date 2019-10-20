package cube.model.food;

import java.util.ArrayList;
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

	public void removeIndex(int index) {
		foodArrayList.remove(index);
	}

	public void removeName(String foodName) {
		for(int i = 0; i < foodArrayList.size(); i ++) {
			if (foodArrayList.get(i).getName().equals(foodName)) {
				foodArrayList.remove(i);
				break;
			}
		}
	}

	public int removeType(String foodType) {
		int count = 0;
		for(int i = 0; i < foodArrayList.size(); i ++) {
			if (foodArrayList.get(i).getType().equals(foodType)) {
				foodArrayList.remove(i);
				count ++;
			}
		}
		return count;
	}

	public boolean existsName(String foodName) {
		for(Food food : foodArrayList) {
			if (food.getName().equals(foodName)) {
				return true;
			}
		}
		return false;
	}

	public boolean existsType(String foodType) {
		for(Food food : foodArrayList) {
			if (food.getType().equals(foodType)) {
				return true;
			}
		}
		return false;
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

	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < foodArrayList.size(); i++) {
			result += String.format("%1$d. %2$s.\n", i + 1, foodArrayList.get(i));
		}
		return result;
	}

}