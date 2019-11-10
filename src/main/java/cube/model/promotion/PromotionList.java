// @@author parvathi14

package cube.model.promotion;

import java.util.ArrayList;

public class PromotionList {
    private ArrayList<Promotion> promotionArrayList;

    public PromotionList() {
        this.promotionArrayList = new ArrayList<>();
    }

    public PromotionList(ArrayList<Promotion> list) {
        this.promotionArrayList = list;
    }

    public void add(Promotion promotion) {
        this.promotionArrayList.add(promotion);
    }

    public ArrayList<Promotion> getPromotionList() {
        return this.promotionArrayList;
    }

    public Promotion get(int index) {
        return promotionArrayList.get(index);
    }

    public Promotion get(String foodName) {
        for (Promotion promotion : promotionArrayList) {
            if (promotion.getName().equals(foodName)) {
                return promotion;
            }
        }
        return null;
    }

    public void removeIndex(int index) {
        promotionArrayList.remove(index);
    }

    public void removeName(String foodName) {
        for (int i = 0; i < promotionArrayList.size(); i++) {
            if (promotionArrayList.get(i).getName().equals(foodName)) {
                promotionArrayList.remove(i);
                break;
            }
        }
    }

    public boolean existsName(String foodName) {
        for (Promotion promotion : promotionArrayList) {
            if (promotion.getName().equals(foodName)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return promotionArrayList.size();
    }

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
