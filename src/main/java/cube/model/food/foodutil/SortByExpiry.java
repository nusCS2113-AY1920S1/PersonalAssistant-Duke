//@@author tygq13
package cube.model.food.foodutil;

import cube.model.food.Food;

import java.util.Comparator;
import java.util.Date;

/**
 * This is a helper class for sorting food by expiry date.
 */
public class SortByExpiry implements Comparator<Food> {
	/**
	 * Sort by food's expiry date.
	 * @param a The first food to be compared.
	 * @param b The second food to be compared.
	 * @return -1 if smaller, 0 if equal, 1 if larger.
	 */
	public int compare(Food a, Food b) {
		Date first = a.getExpiryDate();
		Date second = b.getExpiryDate();
		if (first == null && second == null) {
			return 0;
		} else {
			return first == null ? 1 : (second == null) ? -1 : first.compareTo(second);
		}
	}
}
