package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.IngredientsList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

public class AddOrderCommand extends Command {

    private Order order;

    /**
     * The constructor method for AddOrderCommand.
     *
     * @param order : the {@link Order} to be added in the list
     */
    public AddOrderCommand(Order order) {
        this.order = order;
    }

    /**
     * Public method used to add a new order in the orderList, and write it on the hard disc.
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param os  {@link Storage} writes in the file on the hard disc
     * @throws DukeException Error while adding the command to the duke.txt file
     */
    @Override
    public void execute(IngredientsList il, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        orderList.addEntry(order);
        ui.showLine();
        ui.showAddOrder(order.toString(), orderList.size());

        // TODO: store the new order into file
    }
}
