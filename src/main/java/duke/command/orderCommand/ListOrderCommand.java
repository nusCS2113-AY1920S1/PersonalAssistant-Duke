package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.ListCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Convert;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Date;
import java.util.List;

/**
 * Represents a specific {@link Cmd} used to list all the {@link Order}s in the {@link OrderList}.
 *
 */
public class ListOrderCommand extends ListCommand<Order> {

    private String listType;

    /**
     * the constructor method of {@link ListOrderCommand}
     * @param command type of list: list all orders, list all undone orders,
     *                list all today's orders, list all undone today's orders.
     */
    public ListOrderCommand(String command) {
        this.listType = command;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size() == 0) { throw new DukeException("No orders in the order list!");}
        List<Order> filtered = parse((OrderList) orderList);
        if (filtered.size()==0) { throw new DukeException("No orders found!");}
        else if (filtered.size()==1) { System.out.println("\t Here are the order in the order list:"); }
        else System.out.println("\t Here are the orders in the order list:");
        int cnt = 1;
        for (Order order: filtered) { // looping to print all the saved orders
            ui.showTask("\t " + cnt + "." + order.toString());
            cnt++;
        }
    }

    public List<Order> parse(OrderList orderList) {
        List<Order> tmp = null;
        if (listType.equals("all")) {
            tmp = orderList.getAllEntries();
        } else if (listType.equals("undone")) {
            tmp = orderList.getAllUndoneOrders();
        } else if (listType.equals("today")) {
            tmp = orderList.getTodayOrders();
        } else if (listType.equals("undoneToday")) {
            tmp = orderList.getTodayUndoneOrders();
        } else {
            String s =listType;
            if (s.split("\\/", 3).length==3) {
                Date date = Convert.stringToDate(listType);
                tmp = orderList.findOrderByDate(date);
            } else {
                String dishName = listType;
                tmp = orderList.findOrderByDishes(dishName);
            }
        }
        return tmp;
    }



}
