package duke.command;

import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.IngredientsList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

public abstract class Command {

    public void execute(IngredientsList il, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        throw new DukeException(" NANI??? Looks like one of our developer used the abstract class Cmd!");
    }

    public boolean isExit() {
        return false;
    }
}
