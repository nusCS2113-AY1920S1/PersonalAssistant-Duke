package duke.command.orderCommand;

import duke.command.Command;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;

/**
 * Represents a specific {@link Command} used to cancel/delete a {@link Order} from the {@link OrderList}.
 */
public class DeleteOrderCommand extends Command<Order> {
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
    public void execute(GenericList<Order> orderList, Ui ui, Storage orderStorage) throws DukeException, IOException {
        if (orderList.size()==0) { throw new DukeException("No order in the list! No order can be removed!"); }
        if (orderIndex < orderList.size() && orderIndex >= 0) {
            Order removed = orderList.getEntry(orderIndex);
            //to do
            //update chef's to do list

            ui.showRemovedOrder(removed.toString(), orderList.size());
            orderList.removeEntry(orderIndex);
            orderStorage.removeFromFile(orderIndex+1);
        } else {
            throw new DukeException("Please enter a valid order number between 1 and " + orderList.size() + " to remove");
        }
    }

}
