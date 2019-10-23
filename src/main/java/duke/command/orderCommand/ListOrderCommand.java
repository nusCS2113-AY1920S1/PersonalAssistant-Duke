package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.ListCommand;
import duke.dish.Dish;
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
        List<Order> filtered = parse((OrderList) orderList);

        if (orderList.size() == 0 || filtered == null) { throw new DukeException("No orders found!");}
        System.out.println("\t Here are the orders in the order list:");
        int cnt = 1;
        for (Order order: filtered) { // looping to print all the saved orders
            ui.showTask("\t " + cnt + "." + order.toString());
            cnt++;
        }
    }

    public List<Order> parse(OrderList orderList) {
        List<Order> tmp = null;
        if (listType == "undone") {
            tmp = orderList.getAllUndoneOrders();
        } else if (listType == "today") {
            tmp = orderList.getTodayOrders();
        } else if (listType == "undoneToday") {
            tmp = orderList.getTodayUndoneOrders();
        } else if (listType.startsWith("date")) {
            String date = listType.split(" ",2)[1];
            tmp = orderList.findOrderByDate(date);
        } else if (listType.startsWith("dishes")) {
            String dishName = listType.split(" ",2)[1];

            //to do
            //should use a function to find <Dish> dishes by the string dishName

            Dish dishes = null;
            tmp = orderList.findOrderByDishes(dishes);
        } else {
            tmp = orderList.getAllEntries();
        }


        return tmp;
    }



}
