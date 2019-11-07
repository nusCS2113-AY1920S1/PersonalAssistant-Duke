import duke.command.dishesCommand.AddDishCommand;
import duke.dish.Dish;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DishTest {

    @Test
    public void putDish() {
        Dish dish = new Dish("chicken rice");
        assertEquals("chicken rice", new AddDishCommand(dish));
    }

    @Test
    public void removeDish() {

    }
}