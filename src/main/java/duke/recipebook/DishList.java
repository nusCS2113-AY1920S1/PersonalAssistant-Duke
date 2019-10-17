package duke.recipebook;

import java.util.ArrayList;
import java.util.List;

public class DishList extends Dishes {
    private static List<Dishes> dishlist;

    public DishList() {
        dishlist = new ArrayList<>();
    }

    public static void addDish(Dishes dishName) {
//        if(dishlist.contains(dishName)) {
//            //do nothing
//        }
//        else {
//            dishlist.add(dishName);
//        }
        dishlist.add(dishName);
    }

    public static void deleteDish(int Nb) {
        dishlist.remove(Nb);
    }

    public static Dishes getDish(int Nb) {
        return dishlist.get(Nb);
    }

    public static int getSize() {
        return dishlist.size();
    }

    public void clearList() {
        dishlist.clear();
    }

    public String toString(Dishes dish) {
        return dish.toString();
    }
}
