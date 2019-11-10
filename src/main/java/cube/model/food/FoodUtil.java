package cube.model.food;

import java.util.Comparator;
import java.util.Date;

/**
 * This is a helper class for sorting food by name.
 */
class SortByName implements Comparator<Food> {
	/**
	 * Sort by food name.
	 * @param a The first food to be compared.
	 * @param b The second food to be compared.
	 * @return -1 if smaller, 0 if equal, 1 if larger.
	 */
	public int compare(Food a, Food b) {
		return a.getName().compareTo(b.getName());
	}
}

/**
 * This is a helper class for sorting food by expiry date.
 */
class SortByExpiry implements Comparator<Food> {
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

/**
 * This is a helper class for sorting food by stock.
 */
class SortByStock implements Comparator<Food> {
	/**
	 * Sort by food stock.
	 * @param a The first food to be compared.
	 * @param b The second food to be compared.
	 * @return -1 if smaller, 0 if equal, 1 if larger.
	 */
	public int compare(Food a, Food b) {
		return a.getStock() < b.getStock() ? -1 : (a.getStock() > b.getStock()) ? 1 : 0;
	}
}


/**
 * This is a helper class for sorting food by price.
 */
class SortByPrice implements Comparator<Food> {
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