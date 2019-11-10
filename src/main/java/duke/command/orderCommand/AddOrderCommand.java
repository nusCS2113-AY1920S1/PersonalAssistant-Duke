package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
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
     *
     * @param fridge
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param orderStorage  {@link Storage} writes in the file on the hard disc
     * @throws DukeException Error while adding the command to the duke.txt file
     */
    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage orderStorage, RecipeStorage rs) throws DukeException, IOException {
        orderList.addEntry(order);
        orderList.getTodoList().addTodoFromOrder(order);
        orderStorage.addInFile(order.printInFile());

        ui.showLine();
        ui.showAddOrder(order.toString(), orderList.size());
        ui.showLine();
    }
}
