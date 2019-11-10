package duke.command.orderCommand;

import duke.command.Command;
import duke.command.ingredientCommand.ListCommand;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.Order;
import duke.order.OrderList;
import duke.parser.Convert;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a specific {@link Command} used to list all the {@link Order}s in the {@link OrderList}.
 */
public class ListOrderCommand extends ListCommand {

    private String[] listType;

    /**
     * the constructor method of {@link ListOrderCommand}
     * @param command type of list: list all orders, list all undone orders,
     *                list all today's orders, list all undone today's orders.
     */
    public ListOrderCommand(String[] command) {
        this.listType = command;
    }

    @Override
    public void execute(Fridge fridge, DishList dl, OrderList orderList, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        if (orderList.size() == 0) { throw new DukeException("No orders in the order list!");}
        List<Order> filtered = parse(orderList);
        if (filtered.size()==0) { throw new DukeException("No orders found");}
        ui.showLine();
        if (filtered.size()==1) { System.out.println("\t Here are the order in the order list:"); }
        else System.out.println("\t Here are the orders in the order list:");
        int cnt = 1;
        for (Order order: filtered) { // looping to print all the saved orders
            ui.showTask("\t " + cnt + "." + order.toString());
            cnt++;
        }
        ui.showLine();
    }

    public List<Order> parse(OrderList orderList) throws DukeException {
        if (listType[0].equals("-l")) {
            if (listType[1].equals("all"))  {return orderList.getAllEntries();}
            else if (listType[1].equals("undone")) {return orderList.getAllUndoneOrders();}
            else if (listType[1].equals("today")) {return orderList.getTodayOrders();}
            else if (listType[1].equals("undoneToday")) {return orderList.getTodayUndoneOrders();}
            else {throw new DukeException("Must enter a valid list type");}
        }
        List<Order> tmp = new ArrayList<>();
        if (listType[0].equals("-n")) {
            List<Order> tmp2 = orderList.findOrderByDishes(listType[1]);
            for (Order order: tmp2) { if(!order.isDone()&&order.isToday()) {tmp.add(order);} }
        } else if (listType[0].equals("-d")) {
            String[] orderAndType = listType[1].split(" -l ",2);
            Date date = Convert.stringToDate(orderAndType[0]);
            List<Order> tmp2 = orderList.findOrderByDate(date);
            if (orderAndType.length==1) tmp = tmp2;
            else if (orderAndType[1].equals("undone")) {
                for (Order order: tmp2) { if(!order.isDone()) tmp.add(order); }
            } else { throw new DukeException("Only support find fixed date orders among all/undone orders");}
        } else { throw new DukeException("Must follow the format of list command template");}
        return tmp;
    }
}
