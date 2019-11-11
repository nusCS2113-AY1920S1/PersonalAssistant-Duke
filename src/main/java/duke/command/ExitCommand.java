package duke.command;

import duke.list.DishList;
import duke.fridge.Fridge;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} that indicates program termination
 *
 * @author Sara Djambazovska
 */
public class ExitCommand extends Command {

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) {
        System.out.println("\t Bye Chef. Hope to see you again soon!");
    }
}
