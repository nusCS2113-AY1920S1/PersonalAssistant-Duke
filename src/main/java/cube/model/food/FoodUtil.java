package cube.model.food;

import java.util.Comparator;
import cube.model.food.Food;
import java.util.Date;

class SortByName implements Comparator<Food> {
	public int compare(Food a, Food b) {
		return a.getName().compareTo(b.getName());
	}
}

class SortByExpiry implements Comparator<Food> {
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

class SortByStock implements Comparator<Food> {
	public int compare(Food a, Food b) {
		return a.getStock() > b.getStock() ? -1 : (a.getStock() < b.getStock()) ? 1 : 0;
	}
}