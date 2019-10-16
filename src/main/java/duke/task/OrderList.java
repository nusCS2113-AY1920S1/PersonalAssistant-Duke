package duke.task;

import duke.Duke;
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
    public OrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * The constructor method(2) for OrderList.
     */
    public OrderList() {
        this.orderList = new ArrayList<>();
    }

    /**
     * Adds a order to the {@link OrderList}.
     * @param order {@link Order} to be added to the list
     */
    public void addOrder(Order order) {
        orderList.add(order);
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
     * Not including orders that has been done.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getAllOrders() {
        return orderList;
    }

    /**
     * Returns the removed {@link Order} from position orderNb in the {@link OrderList}.
     * @param orderNb the position of the {@link Order} to be removed from the {@link OrderList}
     * @return Order the order list with the order removed
     */
    public Order removeOrder(int orderNb) {
        return orderList.remove(orderNb);
    }

    public void changeOrderDate(int orderNb, String date) {
        orderList.get(orderNb).setNewDate(date);
    }




}


