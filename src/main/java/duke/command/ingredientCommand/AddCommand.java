package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

public class AddCommand extends Command {

    private Ingredient ingredient;

    public AddCommand(Ingredient i) {
        ingredient = i;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        fridge.addIngredient(ingredient);
        ui.showAddCommand(fridge.getIngredient(ingredient).toString(), fridge.numberOfIngredients());
        fs.update();
    }
}