package duke.order;

import duke.Duke;
import duke.exception.DukeException;
import duke.Dishes.Dishes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of {@link Order}s added by {@link Duke}.
 */
public class OrderList {
    private List<Order> orderList;

    /**
     * The constructor method(1) for OrderList.
     */
    public OrderList() {
        this.orderList = new ArrayList<>();
    }

    /**
     * The constructor method(2) for OrderList.
     */
    public OrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * Adds a order to the {@link OrderList}.
     * @param order {@link Order} to be added to the list
     */
    public void addOrder(Order order) {
        orderList.add(order);
    }

    /**
     * Returns the removed {@link Order} from position orderNb in the {@link OrderList}.
     * @param orderNb the position of the {@link Order} to be removed from the {@link OrderList}
     * @return Order the order list with the order removed
     */
    public Order removeOrder(int orderNb) {
        return orderList.remove(orderNb);
    }

    /**
     * Returns the number of {@link Order}s in the {@link OrderList} so far.
     * @return an integer indicating the size of the list of {@link Order}s stored
     */
    public int size() {
        return orderList.size();
    }

    /**
     * Marks a order as completed if the user finished it.
     * @param orderNb the number of the {@link Order} in the {@link OrderList} that was completed
     */
    public void markOrderDone(int orderNb) {
        orderList.get(orderNb).markAsDone();
    }

    /**
     * Returns the {@link Order} at the position indicated by the orderNb.
     * @param orderNb the position of the {@link Order} requested in the {@link OrderList}
     * @return the requested {@link Order}
     */
    public Order getOrder(int orderNb) {
        return orderList.get(orderNb);
    }

    /**
     * Returns a list of all the {@link Order}s in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getAllOrders() {
        return orderList;
    }

    /**
     * Returns a list of all the undone {@link Order}s in the {@link OrderList}.
     * Not including orders that has been done.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getAllUndoneOrders() {
        List<Order> undoneOrderList = null;
        for (Order order : orderList) {
            if (!order.isDone()) { undoneOrderList.add(order); }
        }
        return undoneOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s today in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getTodayOrders() {
        List<Order> todayOrderList = null;
        for (Order order : orderList) {
            if (order.isToday()) { todayOrderList.add(order); }
        }
        return todayOrderList;
    }

    /**
     * Returns a list of all the undone {@link Order}s today
     * in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getTodayUndoneOrders() {
        List<Order> todayOrderList = null;
        for (Order order : orderList) {
            if (order.isToday()&&(!order.isDone())) { todayOrderList.add(order); }
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
        orderList.get(orderNb).setDate(newDate);
    }

    /**
     * Add dishes to the {@link Order}.
     * Add one more if not specifying the amount.
     * @param orderNb order index
     * @param dish dishes
     */
    public void addOrderDish(int orderNb, Dishes dish) {
        orderList.get(orderNb).addDish(dish);
    }

    /**
     * Add dishes to the {@link Order}.
     * @param orderNb order index
     * @param dish dishes
     * @param amount add amount of that dishes
     */
    public void addOrderDish(int orderNb, Dishes dish, int amount) {
        orderList.get(orderNb).addDish(dish, amount);
    }

    /**
     * Find dishes amount in the {@link Order}.
     * @param orderNb order index
     * @param dishes dishes
     * @return the amount of that dishes
     */
    public int findDishesAmount(int orderNb, Dishes dishes) {
        return orderList.get(orderNb).getDishesAmount(dishes);
    }

    /**
     * clear the whole order list.
     */
    public void clearList() {
        orderList.clear();
    }

}


