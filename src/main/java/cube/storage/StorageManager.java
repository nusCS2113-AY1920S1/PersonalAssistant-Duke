package cube.storage;

import cube.model.food.Food;
import cube.model.food.FoodList;
import java.util.ArrayList;

// this is facade design pattern
// reference: https://github.com/nusCS2113-AY1920S1/addressbook-level3/tree/master/src/main/java/seedu/address/storage

// for testing only;
public class StorageManager {
	private FoodStorage foodStorage;
	private RevenueStorage revenueStorage;

	public StorageManager(FoodStorage foodStorage, RevenueStorage revenueStorage) {
	}

	public ArrayList<Food> loadFood() {
		return null;
	}

	public int loadRevenue() {
		return 0;
	}

	public void appendFood(Food food) {

	}

	public void storeFoodList(FoodList foodlist) {

	}

	public void storeRevenue(double revenue) {
		
	}
}