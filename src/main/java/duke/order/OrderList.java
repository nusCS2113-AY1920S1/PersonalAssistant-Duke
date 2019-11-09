package duke.order;

import duke.Duke;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.list.TodayTodoList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a list of {@link Order}s added by {@link Duke}.
 */
public class OrderList extends GenericList<Order> {

    private TodayTodoList todoList;

    /**
     * The constructor method(1) for OrderList.
     */
    public OrderList() {
        super();
        this.todoList = new TodayTodoList();
    }

    /**
     * The constructor method(2) for OrderList.
     */
    public OrderList(List<Order> orderList) {
        super(orderList);
        this.todoList = new TodayTodoList(orderList);
    }

    /**
     * @return todoList, Chef's today ToDo list
     */
    public TodayTodoList getTodoList() { return this.todoList; }

    /**
     * init chef's today Todo list
     */
    public void initTodoList() { this.todoList = new TodayTodoList(); }

    /**
     * update chef's today todoList
     * add all ordered dishes in the order list into empty todoList
     */
    public void updateTodoList() {
        for (Order order : genList) {
            if (order.isToday()&&(!order.isDone()))
                todoList.addTodoFromOrder(order);
        }

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
        List<Order> undoneOrderList = new ArrayList<>();
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
        List<Order> todayOrderList = new ArrayList<>();
        for (Order order : genList) {
            if (order.isToday()) { todayOrderList.add(order); }
        }
        return todayOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s on some date in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> findOrderByDate(Date queryDate) {
        List<Order> theOrderList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String qDate = simpleDateFormat.format(queryDate);
        for (Order order : genList) { if (qDate.equals(order.getDate())) { theOrderList.add(order); } }
        return theOrderList;
    }

    /**
     * Returns a list of all the {@link Order}s containing some dishes in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> findOrderByDishes(String dishName) {
        List<Order> theOrderList = new ArrayList<>();
        for (Order order : genList) {
            if (order.hasDishes(dishName)) { theOrderList.add(order); }
        }
        return theOrderList;
    }

    /**
     * Returns a list of all the undone {@link Order}s today
     * in the {@link OrderList}.
     * @return  {@link ArrayList} of {@link Order}
     */
    public List<Order> getTodayUndoneOrders() {
        List<Order> todayOrderList = new ArrayList<>();
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
    public void changeOrderDate(int orderNb, Date newDate) throws DukeException {
        genList.get(orderNb).setDate(newDate);
    }

    /**
     * Add dishes to the {@link Order}.
     * Add one more if not specifying the amount.
     * @param orderNb order index
     * @param dishName dishes
     */
    public void addOrderDish(int orderNb, String dishName) {
        genList.get(orderNb).addDish(dishName);
    }

    /**
     * Add dishes to the {@link Order}.
     * @param orderNb order index
     * @param dishName dishes
     * @param amount add amount of that dishes
     */
    public void addOrderDish(int orderNb, String dishName, int amount) {
        genList.get(orderNb).addDish(dishName, amount);
    }

    /**
     * Find dishes amount in the {@link Order}.
     * @param orderNb order index
     * @param dishName dishes
     * @return the amount of that dishes
     */
    public int findDishesAmount(int orderNb, String dishName) {
        return genList.get(orderNb).getDishesAmount(dishName);
    }

    /**
     * @return the description of the chef's To'do list today as a string
     */
    public String todoListToString() {
        return todoList.toString();
    }
}


