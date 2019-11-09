package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * @@author x3chillax
 */
public class ChangeAmountCommand extends Command {

    private int index;
    private int newAmount;
    public ChangeAmountCommand(int index, int newAmount)
    {
        this.index = index;
        this.newAmount = newAmount;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException
    {
        if (index <= fridge.numberOfIngredients() && index > 0) {
            fridge.getIngredient(index - 1).changeAmount(newAmount);
            ui.show("You've changed index number " + (index) + "'s amount to " + newAmount);
            fs.update();
            ui.showTask("\t " + index + "." + fridge.getIngredient(index - 1).toString());
        }
        else{
            throw new DukeException("Enter a valid ingredient index number after change, between 1 and " + fridge.numberOfIngredients());
        }
    }
}

