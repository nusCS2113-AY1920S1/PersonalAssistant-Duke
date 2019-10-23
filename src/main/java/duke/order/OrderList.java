package duke.order;

import duke.Duke;
import duke.exception.DukeException;

import duke.list.GenericList;
import duke.dish.Dish;
import duke.parser.Convert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a list of {@link Order}s added by {@link Duke}.
 */
public class OrderList extends GenericList<Order> {

    /**
     * The constructor method(1) for OrderList.
     */
    public OrderList() {
        super();
    }

    /**
     * The constructor method(2) for OrderList.
     */
    public OrderList(List<Order> orderList) {
        super(orderList);
    }


    /**
     * Marks a order as completed if the user finished it.
     * @param orderNb the number of the {@link Order} in the {@link OrderList} that was completed
     */
    public void markOrderDone(int orderNb) {
        genList.get(orderNb).markAsDone();
    }


    /**
     * Returns a list of all the undone {@link Order}s in the {@link OrderList}.
     * Not including orders that has been done.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getAllUndoneOrders() {
        List<Order> undoneOrderList = null;
        for (Order order : genList) {
            if (!order.isDone()) {
                undoneOrderList.add(order);
            }
        }
        return undoneOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s today in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getTodayOrders() {
        List<Order> todayOrderList = null;
        for (Order order : genList) {
            if (order.isToday()) { todayOrderList.add(order); }
        }
        return todayOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s on some date in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> findOrderByDate(String s) {
        List<Order> theOrderList = null;

        Date queryDate = Convert.stringToDate(s);
        LocalDate qDate = queryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate orderDate;

        for (Order order : genList) {
            orderDate = order.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.isEqual(qDate)) { theOrderList.add(order); }
        }

        return theOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s containing some dishes in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> findOrderByDishes(Dish dish) {
        List<Order> theOrderList = null;
        for (Order order : genList) {
            if (order.hasDishes(dish)) { theOrderList.add(order); }
        }
        return theOrderList;
    }

    /**
     * Returns a list of all the undone {@link Order}s today
     * in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getTodayUndoneOrders() {
        List<Order> todayOrderList = null;
        for (Order order : genList) {
            if (order.isToday()&&(!order.isDone())) {
                todayOrderList.add(order);
            }
        }
        return todayOrderList;
    }

    /**
     * Used to alter the serving date of the {@link Order}.
     * @param orderNb order index
     * @param newDate reset date of the {@link Order}.
     * @throws DukeException if the date is before the date today.
     */
    public void changeOrderDate(int orderNb, String newDate) throws DukeException {
        genList.get(orderNb).setDate(newDate);
    }

    /**
     * Add dishes to the {@link Order}.
     * Add one more if not specifying the amount.
     * @param orderNb order index
     * @param dish dishes
     */
    public void addOrderDish(int orderNb, Dish dish) {
        genList.get(orderNb).addDish(dish);
    }

    /**
     * Add dishes to the {@link Order}.
     * @param orderNb order index
     * @param dish dishes
     * @param amount add amount of that dishes
     */
    public void addOrderDish(int orderNb, Dish dish, int amount) {
        genList.get(orderNb).addDish(dish, amount);
    }

    /**
     * Find dishes amount in the {@link Order}.
     * @param orderNb order index
     * @param dishes dishes
     * @return the amount of that dishes
     */
    public int findDishesAmount(int orderNb, Dish dishes) {
        return genList.get(orderNb).getDishesAmount(dishes);
    }


}


