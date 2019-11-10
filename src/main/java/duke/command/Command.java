package duke.command;

import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.io.IOException;

public abstract class Command {

    //@@author CEGLincoln
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException, IOException {
        throw new DukeException(" NANI??? Looks like one of our developer used the abstract class Cmd!");
    }

    public boolean isExit() {
        return false;
    }
}
