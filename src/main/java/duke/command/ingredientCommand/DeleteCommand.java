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

import java.io.IOException;

public class DeleteCommand extends Command {

    private int taskNb;

    public DeleteCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(IngredientsList ingredientsList, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        if (taskNb <= ingredientsList.size() && taskNb > 0) {
            Ingredient removed = ingredientsList.removeEntry(taskNb - 1);
            try {
                fs.removeFromFile(taskNb - 1);
            } catch (IOException e) {
                throw new DukeException("Error while deleting the ingredient from the hard disc");
            }
            ui.showRemovedIngredient(removed.toString(), ingredientsList.size());
        } else {
            throw new DukeException("Enter a valid ingredient index number after delete, between 1 and " + ingredientsList.size());
        }
    }
}
