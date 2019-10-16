package cube.model.food;

import java.util.Comparator;
import cube.model.food.Food;

class SortByName implements Comparator<Food> {
	public int compare(Food a, Food b) {
		return a.getName().compareTo(b.getName());
	}
}

class SortByExpiry implements Comparator<Food> {
	public int compare(Food a, Food b) {
		return a.getExpiryDate().compareTo(b.getExpiryDate());
	}
}

class SortByStock implements Comparator<Food> {
	public int compare(Food a, Food b) {
		return a.getStock() > b.getStock() ? -1 : (a.getStock() < b.getStock()) ? 1 : 0;
	}
}