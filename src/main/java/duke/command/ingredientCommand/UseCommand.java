package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

public class UseCommand extends Command {
    private Ingredient toUse;

    public UseCommand(Ingredient ingredient){
        toUse = ingredient;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        if (fridge.removeIngredient(toUse)) {
            ui.show("Great you used "+ toUse.toStringWithoutDate());
            fs.update();
        } else {
            ui.show("There is not a sufficient amount of " + toUse.getName() + " that is not expired, maybe you could buy some first? ");
        }
    }
}
