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

import java.io.IOException;

public class DeleteCommand extends Command {

    private int taskNb;

    public DeleteCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        if (taskNb <= fridge.numberOfIngredients() && taskNb > 0) {
            Ingredient removed = fridge.removeIngredient(taskNb - 1);
            try {
                fs.removeFromFile(taskNb - 1);
            } catch (IOException e) {
                throw new DukeException("Error while deleting the ingredient from the hard disc");
            }
            ui.showRemovedIngredient(removed.toString(), fridge.numberOfIngredients());
        } else {
            throw new DukeException("Enter a valid ingredient index number after delete, between 1 and " + fridge.numberOfIngredients());
        }
    }
}
