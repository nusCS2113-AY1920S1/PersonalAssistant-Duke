package duke.command.orderCommand;

import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.ingredient.IngredientsList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a specific {@link Command} used to cancel/delete a {@link Order} from the {@link OrderList}.
 */
public class DeleteOrderCommand extends Command {
    private int orderIndex;

    /**
     * the constructor method of class {@link DeleteOrderCommand}
     *
     * @param orderNumber order number in the order list
     */
    public DeleteOrderCommand(int orderNumber) {
        this.orderIndex = orderNumber;
    }

    @Override
    public void execute(IngredientsList il, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        if (orderList.size()==0) { throw new DukeException("No order in the list! No order can be removed!"); }
        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order removed = orderList.getEntry(orderIndex);
            // TODO: update chef's to do list
            ui.showRemovedOrder(removed.toString(), orderList.size());
            orderList.removeEntry(orderIndex);
            try {
                os.removeFromFile(orderIndex + 1);
            } catch (IOException e) {
                throw new DukeException(e.getMessage());
            }
        } else {
            throw new DukeException("Please enter a valid order number between 1 and " + orderList.size() + " to remove");
        }
    }
}
