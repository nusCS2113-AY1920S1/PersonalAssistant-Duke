package duke.orderCommand;

import duke.exception.DukeException;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link OrderCommand} used to list all the {@link Order}s in the {@link OrderList}.
 */
public class ListAllOrders extends OrderCommand {

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size() == 0) {
            throw new DukeException("No orders yet!");
        } else {
            System.out.println("\t Here are the orders in your list:");
            for (int i = 1; i <= orderList.size(); i++) { // looping to print all the saved tasks
                ui.showTask("\t " + i + "." + orderList.getOrder(i - 1).toString());
            }
        }
    }
}
