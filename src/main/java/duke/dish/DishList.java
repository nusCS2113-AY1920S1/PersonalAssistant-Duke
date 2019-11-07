package duke.dish;

import duke.list.GenericList;

import java.util.ArrayList;
import java.util.List;

/**
 * HAHA :p This is mine now boys.
 * But is there a point in doing this?...
 *
 * @@author CEGLincoln
 */
public class DishList extends GenericList<Dish> {

    private List<Dish> recipe;

    public DishList(List<Dish> dishList){
        super(dishList);
        recipe = dishList;
    }

    public DishList() {
       super();
       recipe = new ArrayList<>(); // I don't like you
    }

    public String toString(){
        String str = "Recipe Book";
        for (Dish d : recipe) {
            str += "\n" + d.toString();
        }
        return str;
    }

    public String printInFile() {
        String str = "";
        for (Dish d : recipe) {
            str += "\n" + d.toString();
        }
        return str;
    }
}
