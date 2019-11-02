/**
 * Handling of JSON File Read/Write operations in Cube
 *
 * @author kuromono
 */
package cube.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cube.model.food.FoodList;
import cube.model.promotion.PromotionList;
import cube.model.sale.SalesHistory;

public class StorageManager {
	@JsonProperty
	private FoodStorage foodStorage;
	@JsonProperty
	private RevenueStorage revenueStorage;
	@JsonProperty
	private ConfigStorage configStorage;
	@JsonProperty
	private SaleStorage saleStorage;
	@JsonProperty
	private PromotionStorage promotionStorage;

	/**
	 * Default constructor.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public StorageManager() {
		this.foodStorage = new FoodStorage();
		this.revenueStorage = new RevenueStorage();
		this.configStorage = new ConfigStorage();
		this.saleStorage = new SaleStorage();
		this.promotionStorage = new PromotionStorage();
	}

	/**
	 * Constructor with multiple arguments.
	 * Creates a new instance of Food & Revenue Storage Classes.
	 */
	public StorageManager(FoodStorage foodStorage, RevenueStorage revenueStorage, SaleStorage saleStorage, PromotionStorage promotionStorage, ConfigStorage configStorage) {
		this.foodStorage = foodStorage;
		this.revenueStorage = revenueStorage;
		this.saleStorage = saleStorage;
		this.promotionStorage = promotionStorage;
		this.configStorage = configStorage;
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

	/**
	 * Retrieves the past sales histories stored in SaleStorage.
	 * @return SalesHistory object containing past sale histories stored in SaleStroage.
	 */
	@JsonIgnore
	public SalesHistory getSalesHistory() {
		return saleStorage.getSalesHistory();
	}

	/**
	 * Stores the SalesHistory object into SaleStorage.
	 * @param salesHistory SalesHistory object to be stored in the SaleStorage.
	 */
	public void storeSalesHistory(SalesHistory salesHistory) {
		saleStorage.storeSalesHistory(salesHistory);
	}

	/**
	 * Retrieves the past sales histories stored in SaleStorage.
	 * @return SalesHistory object containing past sale histories stored in SaleStroage.
	 */
	@JsonIgnore
	public PromotionList getPromotionList() {
		return promotionStorage.getPromotionList();
	}

	/**
	 * Stores the SalesHistory object into SaleStorage.
	 * @param promotionList SalesHistory object to be stored in the SaleStorage.
	 */
	public void storePromotionList(PromotionList promotionList) {
		promotionStorage.storePromotionList(promotionList);
	}

	/**
	 * Retrieves the configuration storage containing user-defined configurations.
	 * @return ConfigStorage object containing the user-defined configurations.
	 */
	@JsonIgnore
	public ConfigStorage getConfig() {
		return configStorage;
	}

	/**
	 * Sets the configuration storage containing user-defined configurations.
	 * @return ConfigStorage object containing the user-defined configurations.
	 */
	@JsonIgnore
	public void setConfig(ConfigStorage configStorage) {
		this.configStorage = configStorage;
	}

}