package duke.command.ingredientCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.ingredient.IngredientsList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

//Exits the program
public class ExitCommand extends Command {

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(IngredientsList il, DishList dl, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) {
        System.out.println("\t Bye. Hope to see you again soon!");
    }
}
