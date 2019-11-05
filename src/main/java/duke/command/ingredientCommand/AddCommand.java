package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

public class AddCommand extends Command {

    private Ingredient ingredient;

    public AddCommand(Ingredient i) {
        ingredient = i;
    }

    @Override
    public void execute(IngredientsList il, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        il.addEntry(ingredient);
        ui.showAddCommand(il.getEntry(ingredient).toString(), il.size());
        fs.update();
    }
}