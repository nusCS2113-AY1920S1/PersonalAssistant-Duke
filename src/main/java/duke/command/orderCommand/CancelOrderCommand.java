package duke.command.orderCommand;

import duke.command.Command;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * @author VirginiaYu
 *
 * Represents a specific {@link Command} used to cancel an undone {@link Order} from the {@link OrderList}.
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

    /**
     * Public method used to cancel an existing undone order in the orderList, and delete it from the hard disc.
     * Print out corresponding info if removing successfully
     *
     * @param orderList the {@link OrderList} to be expanded
     * @param ui       {@link Ui} used for printing the order output
     * @param orderStorage  {@link OrderStorage} writes in the file on the hard disc
     * @throws DukeException Error while cancelling the order, or updating to the hard disc
     */
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
            if (orderList.size()==1) {
                throw new DukeException("Got only 1 order in the order list.\n\t Enter '1' as order index");
            } else {
                throw new DukeException("Must enter a valid order index number between 1 and "+orderList.size());
            }

        }
    }
}
