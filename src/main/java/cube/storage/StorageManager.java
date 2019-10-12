package cube.storage;

import cube.model.food.Food;
import cube.model.food.FoodList;

// this is facade design pattern
// reference: https://github.com/nusCS2113-AY1920S1/addressbook-level3/tree/master/src/main/java/seedu/address/storage

public class StorageManager {
	private FoodStorage foodStorage;
	private RevenueStorage revenueStorage;

	public StorageManager() {
		this.foodStorage = new FoodStorage();
		this.revenueStorage = new RevenueStorage();
	}

	public StorageManager(FoodStorage foodStorage, RevenueStorage revenueStorage) {
		this.foodStorage = foodStorage;
		this.revenueStorage = revenueStorage;
	}

	public FoodList getFoodList() {
		return foodStorage.getFoodList();
	}

	public void appendFood(Food food) {
		foodStorage.appendFood(food);
	}

	public void storeFoodList(FoodList foodlist) {
		foodStorage.storeFoodList(foodlist);
	}

	public double getRevenue() {
		return revenueStorage.getRevenue();
	}

	public void storeRevenue(double revenue) {
		revenueStorage.storeRevenue(revenue);
	}

}