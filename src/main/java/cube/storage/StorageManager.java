/**
 * Handling of JSON File Read/Write operations in Cube
 *
 * @author kuromono
 */
package cube.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cube.model.Food;
import cube.model.FoodList;

public class StorageManager {
	@JsonProperty
	private FoodStorage foodStorage;
	@JsonProperty
	private RevenueStorage revenueStorage;

	/**
	 * Default constructor.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public StorageManager() {
		this.foodStorage = new FoodStorage();
		this.revenueStorage = new RevenueStorage();
	}

	/**
	 * Constructor with 2 arguments.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public StorageManager(FoodStorage foodStorage, RevenueStorage revenueStorage) {
		this.foodStorage = foodStorage;
		this.revenueStorage = revenueStorage;
	}

	/**
	 * Retrieves the FoodList stored in the FoodStorage.
	 * @return FoodList object stored in the FoodStorage.
	 */
	@JsonIgnore
	public FoodList getFoodList() {
		return foodStorage.getFoodList();
	}

	/**
	 * Appends a Food Item Object into the FoodStorage.
	 */
	public void appendFood(Food food) {
		foodStorage.appendFood(food);
	}

	/**
	 * Stores the specified FoodList object into the FoodStorage.
	 * @param foodlist FoodList object to be stored into the FoodStorage.
	 */
	public void storeFoodList(FoodList foodlist) {
		foodStorage.storeFoodList(foodlist);
	}

	/**
	 * Retrieves the revenue stored in the RevenueStorage.
	 * @return Revenue stored in the RevenueStorage.
	 */
	@JsonIgnore
	public double getRevenue() {
		return revenueStorage.getRevenue();
	}

	/**
	 * Stores the specified revenue into the RevenueStorage.
	 * @param revenue Revenue amount to be stored in the RevenueStorage.
	 */
	public void storeRevenue(double revenue) {
		revenueStorage.storeRevenue(revenue);
	}

}