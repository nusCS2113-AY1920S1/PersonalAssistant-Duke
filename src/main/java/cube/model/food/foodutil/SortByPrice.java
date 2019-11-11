//@@author tygq13
package cube.model.food.foodutil;

import cube.model.food.Food;

import java.util.Comparator;

/**
 * This is a helper class for sorting food by price.
 */
public class SortByPrice implements Comparator<Food> {
	/**
	 * Sort by food price.
	 * @param a The first food to be compared.
	 * @param b The second food to be compared.
	 * @return -1 if smaller, 0 if equal, 1 if larger.
	 */
	public int compare(Food a, Food b) {
		return a.getPrice() < b.getPrice() ? -1 : (a.getPrice() > b.getPrice()) ? 1 : 0;
	}
}
