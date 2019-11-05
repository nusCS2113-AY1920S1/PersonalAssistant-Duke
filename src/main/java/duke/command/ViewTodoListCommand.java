package duke.command;

import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.Storage;
import duke.ui.Ui;

public class ViewTodoListCommand extends Command {

    @Override
    public void execute(IngredientsList il, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        String info = (orderList).todoListToString();
        System.out.println(info);
    }
}
