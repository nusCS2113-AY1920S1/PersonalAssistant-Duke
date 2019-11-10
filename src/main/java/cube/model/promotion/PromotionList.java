// @@author parvathi14

package cube.model.promotion;

import java.util.ArrayList;

/**
 * Model for promotion list.
 */
public class PromotionList {
    private ArrayList<Promotion> promotionArrayList;

    /**
     * Default constructor.
     */
    public PromotionList() {
        this.promotionArrayList = new ArrayList<>();
    }

    /**
     * Constructor with a given list.
     * @param list the given list.
     */
    public PromotionList(ArrayList<Promotion> list) {
        this.promotionArrayList = list;
    }

    /**
     * Add new promotion.
     * @param promotion the promotion to be added.
     */
    public void add(Promotion promotion) {
        this.promotionArrayList.add(promotion);
    }

    /**
     * Get promotion list.
     * @return the promotion list.
     */
    public ArrayList<Promotion> getPromotionList() {
        return this.promotionArrayList;
    }

    /**
     * Get a promotion by index.
     * @param index the index.
     * @return promotion at this index.
     */
    public Promotion get(int index) {
        return promotionArrayList.get(index);
    }

    /**
     * Get a promotion by food name.
     * @param foodName the food name.
     * @return promotion of this food name.
     */
    public Promotion get(String foodName) {
        for (Promotion promotion : promotionArrayList) {
            if (promotion.getName().equals(foodName)) {
                return promotion;
            }
        }
        return null;
    }

    /**
     * Remove promotion by index.
     * @param index the index.
     */
    public void removeIndex(int index) {
        promotionArrayList.remove(index);
    }

    /**
     * Checks whether there is a promotion of this food name.
     * @param foodName the food name.
     * @return true if exists, false otherwise.
     */
    public boolean existsName(String foodName) {
        for (Promotion promotion : promotionArrayList) {
            if (promotion.getName().equals(foodName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the size of the list.
     * @return the size of the list.
     */
    public int size() {
        return promotionArrayList.size();
    }

    /**
     * Clear the list.
     */
    public void clear() {
        promotionArrayList.clear();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < promotionArrayList.size(); i++) {
            result += String.format("%1$d. %2$s.\n", i + 1, promotionArrayList.get(i));
        }
        return result;
    }

    /*
    @Override
    // for Junit test use
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PromotionList // instanceof handles nulls
                && promotionArrayList.equals(((FoodList) other).promotionArrayList));
    }
   */
}
