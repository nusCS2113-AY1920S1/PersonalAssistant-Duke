package cube.storage;

import cube.model.promotion.PromotionList;

public class PromotionStorage {
    private PromotionList promotionList;

    /**
     * Default constructor for PromotionStorage.
     */
    public PromotionStorage() {
        this.promotionList = new PromotionList();
    }

    /**
     * Getter for PromotionList.
     * @return Promotion object containing current promotions.
     */
    public PromotionList getPromotionList() {
        return promotionList;
    }

    /**
     * Setter for PromotionList.
     * @param promotionList object containing promotions.
     */
    public void storePromotionList (PromotionList promotionList) {
        this.promotionList = promotionList;
    }
}