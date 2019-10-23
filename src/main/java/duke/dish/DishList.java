package duke.dish;

import duke.list.GenericList;

import java.util.ArrayList;
import java.util.List;

public class DishList extends GenericList<Dish> {


    public DishList(List<Dish> dishList){
        super(dishList);
    }

    public DishList() {
       super();
    }

}
