package cube.model.food;

import cube.model.food.foolUtil.SortByExpiry;
import cube.model.food.foolUtil.SortByName;
import cube.model.food.foolUtil.SortByPrice;
import cube.model.food.foolUtil.SortByStock;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Model for food list.
 */
public class FoodList {
	private ArrayList<Food> foodArrayList;

	/**
	 * The way to sort.
	 */
	public enum SortType {
		EXPIRY, NAME, STOCK, PRICE;

        public static boolean isDefined(String temp) {
            if (temp.equals("EXPIRY") || temp.equals("NAME")
					|| temp.equals("STOCK") || temp.equals("PRICE")) {
                return true;
            }
            return false;
        }
	}

	/**
	 * Default constructor.
	 */
	public FoodList() {
		this.foodArrayList = new ArrayList<>();
	}

	/**
	 * Constructor with a given list.
	 * @param list the given list.
	 */
	public FoodList(ArrayList<Food> list) {
		this.foodArrayList = list;
	}

	/**
	 * Add food into the list.
	 * @param food the food to add.
	 */
	public void add(Food food) {
		this.foodArrayList.add(food);
	}

	/**
	 * Public getter to allow JSON read/write to work.
	 * @return the food list.
	 */
	public ArrayList<Food> getFoodList() {
		return this.foodArrayList;
	}

	/**
	 * Get food by index.
	 * @param index the index.
	 * @return the food at the index.
	 */
	public Food get(int index) {
		return foodArrayList.get(index);
	}

	/**
	 * Get food by name.
	 * @param foodName the name.
	 * @return the food with the name.
	 */
	public Food get(String foodName) {
		for (Food food : foodArrayList) {
			if (food.getName().toLowerCase().equals(foodName.toLowerCase())) {
				return food;
			}
		}
		return null;
	}

	/**
	 * Remove food by index.
	 * @param index the index to remove.
	 */
	public void removeIndex(int index) {
		foodArrayList.remove(index);
	}

	/**
	 * Remove food by name.
	 * @param foodName the name of the food.
	 */
	public void removeName(String foodName) {
		for (int i = 0; i < foodArrayList.size(); i++) {
			if (foodArrayList.get(i).getName().toLowerCase().equals(foodName.toLowerCase())) {
				foodArrayList.remove(i);
				break;
			}
		}
	}

	/**
	 * Remove food by type.
	 * @param foodType the type of the food.
	 * @return the number of items removed.
	 */
	public int removeType(String foodType) {
		int count = 0;
		for (int i = 0; i < foodArrayList.size();) {
			if (foodArrayList.get(i).getType() != null
					&& foodArrayList.get(i).getType().equals(foodType)) {
				foodArrayList.remove(i);
				count++;
			} else {
				i++;
			}
		}
		return count;
	}

	/**
	 * Checks whether the name exists.
	 * @param foodName the name to check.
	 * @return true if exists, false otherwise.
	 */
	public boolean existsName(String foodName) {
		for (Food food : foodArrayList) {
			if (food.getName().toLowerCase().equals(foodName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the type exists.
	 * @param foodType the type to check.
	 * @return true if exists, false otherwise.
	 */
	public boolean existsType(String foodType) {
		for (Food food : foodArrayList) {
			if (food.getType() != null && food.getType().toLowerCase().equals(foodType.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get list size.
	 * @return the size of the list.
	 */
	public int size() {
		return foodArrayList.size();
	}

	/**
	 * Sort the list by sort type.
	 * @param sortType the way to sort.
	 */
	public void sort(SortType sortType) {
		switch (sortType) {
			case EXPIRY:
				Collections.sort(foodArrayList, new SortByExpiry());
				break;
			case NAME:
				Collections.sort(foodArrayList, new SortByName());
				break;
			case STOCK:
				Collections.sort(foodArrayList, new SortByStock());
				break;
			case PRICE:
				Collections.sort(foodArrayList, new SortByPrice());
				break;
			default:
				break;
		}
	}

	/**
	 * Clear the list.
	 */
	public void clear() {
		foodArrayList.clear();
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < foodArrayList.size(); i++) {
			result += String.format("%1$d. %2$s.\n", i + 1, foodArrayList.get(i));
		}
		return result;
	}

	@Override
	// for Junit test use
	public boolean equals(Object other) {
	    return other == this // short circuit if same object
	            || (other instanceof FoodList // instanceof handles nulls
	                    && foodArrayList.equals(((FoodList) other).foodArrayList));
	}

}