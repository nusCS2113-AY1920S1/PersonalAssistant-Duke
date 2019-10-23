package duke.Dishes;

import java.util.ArrayList;
import java.util.List;

public class DishList {
    private List<Dishes> dishlist;

    public DishList() {
        this.dishlist = new ArrayList<>();
    }

    public void addDish(Dishes dishName) {
        this.dishlist.add(dishName);
    }

    public void deleteDish(int Nb) {
        dishlist.remove(Nb);
    }

    public Dishes getDish(int Nb) {
        return dishlist.get(Nb);
    }

    public int getSize() {
        return dishlist.size();
    }

    public void clearList() {
        dishlist.clear();
    }

    public String toString(Dishes dish) {
        return dish.getDishname();
    }
}