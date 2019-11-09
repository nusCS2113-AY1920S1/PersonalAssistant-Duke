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
public class ChangeNameCommand extends Command {

    private int index;
    private String name;
    public ChangeNameCommand(int index, String newName)
    {
        this.index = index;
        this.name = newName;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException
    {
        if (index <= fridge.numberOfIngredients() && index > 0) {
            fridge.getIngredient(index - 1).setName(name);
            ui.show("You've changed index number " + (index) + "'s name to " + name);
            fs.update();
            ui.showTask("\t " + index + "." + fridge.getIngredient(index - 1).toString());
        }
        else{
            throw new DukeException("Enter a valid ingredient index number after change, between 1 and " + fridge.numberOfIngredients());
        }
    }
}

