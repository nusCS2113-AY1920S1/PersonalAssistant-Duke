package duke.command;

import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

public class ViewTodoListCommand extends Command {

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        String info = orderList.todoListToString();
        System.out.println(info);
    }
}
