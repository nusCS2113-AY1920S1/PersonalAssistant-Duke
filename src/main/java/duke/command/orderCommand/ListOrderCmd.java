package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.ListCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;

/**
 * Represents a specific {@link Cmd} used to list all the {@link Order}s in the {@link OrderList}.
 *
 */
public class ListOrderCmd extends ListCommand<Order> {

    private String listType;

    /**
     * the constructor method of {@link ListOrderCmd}
     * @param command type of list: list all orders, list all undone orders,
     *                list all today's orders, list all undone today's orders.
     */
    public ListOrderCmd(String command) {
        this.listType = command;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        List<Order> tmp = null;
        if (listType == "undone") {
            tmp = ((OrderList)orderList).getAllUndoneOrders();
        } else if (listType == "today") {
            tmp = ((OrderList)orderList).getTodayOrders();
        } else if (listType == "undoneToday") {
            tmp = ((OrderList)orderList).getTodayUndoneOrders();
        } else { tmp = orderList.getAllEntries(); }
        if (orderList.size() == 0 || tmp == null) { throw new DukeException("No orders found!");}
        System.out.println("\t Here are the orders in the order list:");
        int cnt = 1;
        for (Order order: tmp) { // looping to print all the saved orders
            ui.showTask("\t " + cnt + "." + order.toString());
            cnt++;
        }
    }
}
