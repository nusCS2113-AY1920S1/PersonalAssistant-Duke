import duke.command.Command;
import duke.command.dishesCommand.AddDishCommand;
import duke.dish.Dish;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author 9hafidz6
class DishTest {

    @Test
    public void putDish() throws DukeException {
        Dish dish = new Dish("chicken rice");
        Ingredient ingr = new Ingredient("chicken", 10, "1/1/2020");
        dish.addIngredients(ingr,10);
        assertEquals("chicken rice", dish.getDishname());
        assertEquals("chicken", dish.getIngredients(1));
    }

    @Test
    public void removeDish() {
        Dish dish = new Dish("chicken rice");
        DishList dishlist = new DishList();
        dishlist.addEntry(dish);

        assertEquals("no dishes in list", dishlist.removeEntry(1));
    }

    @Test
    public void changeDish() {
        Dish dish = new Dish("chicken ice");
        DishList dishlist = new DishList();

        dishlist.addEntry(dish);
        dishlist.getEntry(1).changeName("chicken rice");
        assertEquals("chicken rice",dishlist.getEntry(1).getDishname());
    }
}