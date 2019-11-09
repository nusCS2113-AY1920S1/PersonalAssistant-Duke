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
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a specific {@link Command} used to cancel/delete a {@link Order} from the {@link OrderList}.
 */
public class CancelOrderCommand extends Command {
    private int orderIndex;

    /**
     * the constructor method of class {@link CancelOrderCommand}
     *
     * @param orderNumber order number in the order list
     */
    public CancelOrderCommand(int orderNumber) {
        this.orderIndex = orderNumber;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage orderStorage, RecipeStorage rs) throws DukeException, IOException {

        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be cancelled!");
        }

        if (orderIndex <= orderList.size() && orderIndex > 0) {
            
            Order removed = orderList.getEntry(orderIndex-1);
            if (removed.isDone()) {
                throw new DukeException("Order "+orderIndex+" has already been done! Cancellation does not make sense!");
            }
            if (removed.isToday()) {
                orderList.getTodoList().deleteTodoFromOrder(removed);
            }
            orderList.removeEntry(orderIndex-1);
            orderStorage.removeFromFile(orderIndex-1);

            ui.showLine();
            ui.showRemovedOrder(removed.toString(), orderList.size());
            ui.showLine();

        } else {
            throw new DukeException("Please enter a valid order number between 1 and " + orderList.size() + " to remove");
        }
    }
}
