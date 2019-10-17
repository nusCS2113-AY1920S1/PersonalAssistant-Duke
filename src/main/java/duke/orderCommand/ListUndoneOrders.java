package duke.orderCommand;

import duke.exception.DukeException;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link OrderCommand} used to list all the undone {@link Order}s in the {@link OrderList}.
 */
public class ListUndoneOrders extends OrderCommand {

    @Override
    public void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size() == 0) {
            throw new DukeException("No orders yet!");
        } else {
            System.out.println("\t Searching for undone orders in your list...");
            int i;
            for (i = 1; i <= orderList.size(); i++) { // looping to print all the saved tasks
                if (!orderList.getOrder(i - 1).isDone()) {
                    ui.showTask("\t " + i + "." + orderList.getOrder(i - 1).toString());
                }
            }
            if (i==1) System.out.println("\t Cannot find undone orders!");
        }
    }
}
