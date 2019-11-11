//@@author tygq13
package cube.model.food.foodutil;

import cube.model.food.Food;

import java.util.Comparator;

/**
 * This is a helper class for sorting food by stock.
 */
public class SortByStock implements Comparator<Food> {
    /**
     * Sort by food stock.
     * @param a The first food to be compared.
     * @param b The second food to be compared.
     * @return -1 if smaller, 0 if equal, 1 if larger.
     */
    public int compare(Food a, Food b) {
        return a.getStock() < b.getStock() ? -1 : (a.getStock() > b.getStock()) ? 1 : 0;
    }
}