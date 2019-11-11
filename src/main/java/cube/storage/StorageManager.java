/**
 * Handling of JSON File Read/Write operations in Cube.
 *
 * @author kuromono
 */

package cube.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cube.model.food.FoodList;
import cube.model.promotion.PromotionList;
import cube.model.sale.SalesHistory;

public class StorageManager {
    @JsonProperty
    private FoodStorage foodStorage;
    @JsonProperty
    private ConfigStorage configStorage;
    @JsonProperty
    private SaleStorage saleStorage;
    @JsonProperty
    private PromotionStorage promotionStorage;

    /**
     * The default constructor.
     * Create a new instance of all of the Storage Classes.
     */
    public StorageManager() {
        this.foodStorage = new FoodStorage();
        this.configStorage = new ConfigStorage();
        this.saleStorage = new SaleStorage();
        this.promotionStorage = new PromotionStorage();
    }

    /**
     * The constructor with multiple arguments.
     * Create a new instance of all Storage Classes.
     */
    public StorageManager(FoodStorage foodStorage, SaleStorage saleStorage,
                          PromotionStorage promotionStorage, ConfigStorage configStorage) {
        this.foodStorage = foodStorage;
        this.saleStorage = saleStorage;
        this.promotionStorage = promotionStorage;
        this.configStorage = configStorage;
    }

    /**
     * Getter for the FoodList.
     * Retrieve the FoodList stored in the FoodStorage.
     *
     * @return FoodList The Food Object stored in the FoodStorage.
     */
    @JsonIgnore
    public FoodList getFoodList() {
        return foodStorage.getFoodList();
    }

    /**
     * Stores the specified FoodList object into the FoodStorage.
     *
     * @param foodlist FoodList object to be stored into the FoodStorage.
     */
    public void storeFoodList(FoodList foodlist) {
        foodStorage.storeFoodList(foodlist);
    }


    /**
     * Getter for the SalesHistory.
     * Retrieve the past Sales Histories stored in the SaleStorage.
     *
     * @return SalesHistory object containing past sale histories stored in SaleStroage.
     */
    @JsonIgnore
    public SalesHistory getSalesHistory() {
        return saleStorage.getSalesHistory();
    }

    /**
     * Stores the SalesHistory object into SaleStorage.
     *
     * @param salesHistory SalesHistory object to be stored in the SaleStorage.
     */
    public void storeSalesHistory(SalesHistory salesHistory) {
        saleStorage.storeSalesHistory(salesHistory);
    }

    /**
     * Getter for the PromotionList.
     * Retrieve the past sales histories stored in SaleStorage.
     *
     * @return SalesHistory object containing past sale histories stored in SaleStroage.
     */
    @JsonIgnore
    public PromotionList getPromotionList() {
        return promotionStorage.getPromotionList();
    }

    /**
     * Stores the SalesHistory object into SaleStorage.
     *
     * @param promotionList SalesHistory object to be stored in the SaleStorage.
     */
    public void storePromotionList(PromotionList promotionList) {
        promotionStorage.storePromotionList(promotionList);
    }

    /**
     * Getter for Config
     * Retrieve the configuration storage containing user-defined configurations.
     *
     * @return ConfigStorage object containing the user-defined configurations.
     */
    @JsonIgnore
    public ConfigStorage getConfig() {
        return configStorage;
    }

    /**
     * Setter for Config.
     * Set the configuration storage containing user-defined configurations.
     */
    @JsonIgnore
    public void setConfig(ConfigStorage configStorage) {
        this.configStorage = configStorage;
    }

}