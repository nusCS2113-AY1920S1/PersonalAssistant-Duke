package duke.recipebook;

import java.util.ArrayList;
import java.util.List;

public class dishlist extends dishes {
    private static List<dishes> dishlist;

    public dishlist() {
        dishlist = new ArrayList<>();
    }

    public static void addDish(dishes name) {
        dishlist.add(name);
    }

    public static int getsize() {
        return dishlist.size();
    }

    public void clearList() {
        dishlist.clear();
    }

    public String toString(dishes dish) {
        return dish.toString();
    }
}
