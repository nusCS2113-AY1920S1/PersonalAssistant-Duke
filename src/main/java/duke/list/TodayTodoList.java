package duke.list;

import duke.exception.DukeException;
import duke.order.Order;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VirginiaYu
 *
 * Represents a chef's today To-Do list. It is realted to
 * the order list and update correspondingly with the order list
 */
public class TodayTodoList {

    /**
     * map's key: dishes name
     * map's value: dishes amount
     */
    private Map<String, Integer> tasks;

    /**
     * The constructor method (1) for {@link TodayTodoList}.
     */
    public TodayTodoList() {
        this.tasks = new LinkedHashMap<>();
    }

    /**
     * The constructor method (2) for {@link TodayTodoList}.
     */
    public TodayTodoList(List<Order> orderList) {
        this.tasks = new LinkedHashMap<>();
        for (Order order: orderList) {
            if (order.isToday()&&(!order.isDone())) {
                addTodoFromOrder(order);
            }
        }
    }

    /**
     * Add dish with its amount into the Todo List
     * If the list contains the dish, then update the amount.
     * Otherwise, add new dishes into list with its ordered amount.
     *
     * @param dish dish name as string
     * @param addAmount adding amount of the dish
     */
    public void addTodo(String dish, int addAmount) {
        if (!tasks.containsKey(dish)) tasks.put(dish, addAmount);
        else {
            int oldAmount = tasks.get(dish);
            tasks.put(dish, oldAmount+addAmount);
        }
    }

    /**
     * Remove dish with the amount out of the Todo List
     * If the list contains the dish and the quantity in the list is more
     * than the removing amount, then update the amount in todo list.
     * Otherwise, shows error to the user.
     *
     * @param dish dish name as string
     * @param delAmount removing amount of the dish
     * @throws DukeException cannot find dishes or there are not enough todo dishes to remove
     */
    public void deleteTodo(String dish, int delAmount) throws DukeException {
        if (!tasks.containsKey(dish)) { throw new DukeException("Error when deleting the task: cannot find dishes in todo list"); }
        int oldAmount = tasks.get(dish);
        if ((oldAmount-delAmount)<0) { throw new DukeException("Error when deleting the task: removing amount exceed actual"); }
        else if (oldAmount==delAmount) tasks.remove(dish);
        else tasks.put(dish, oldAmount-delAmount);
    }

    /**
     * All ordered dishes in a new order need to be add into the Todo List
     * Only to know the order so that can visit the ordered dishes and amount
     * in the order content.
     *
     * @param order a new coming order {@link Order}
     */
    public void addTodoFromOrder(Order order) {
        if (!order.isDone()) {
            for (Map.Entry<String, Integer> entry : order.getOrderContent().entrySet()) {
                String dishName = entry.getKey();
                int amount = entry.getValue();
                addTodo(dishName, amount);
            }
        }
    }

    /**
     * After an order be cancelled or done, dishes in the order
     * are supposed to be removed out of the Todo List
     *
     * @param order a cancelling or just done order {@link Order}
     * @throws DukeException cannot find dishes or there are not enough todo dishes to remove
     */
    public void deleteTodoFromOrder(Order order) throws DukeException {
        if (!order.isDone()) System.out.println("\t Warning: Deleting undone dishes from todo list");
        for (Map.Entry<String, Integer> entry : order.getOrderContent().entrySet()) {
            String dishName = entry.getKey();
            int amount = entry.getValue();
            deleteTodo(dishName, amount);
        }
    }

    /**
     * @return a string that describe the tasks that need to be done
     * (or say, undone) by closing today
     */
    public String toString() {
        Date date = new Date();
        String output="\t Today Task list ("+date+")";
        int cnt=0;
        if (tasks.size()==0) {
            output+="\n\t None!";
        }
        for (Map.Entry<String, Integer> entry : tasks.entrySet()) {
            cnt++;
            String dishName = entry.getKey();
            int amount = entry.getValue();
            output+="\n\t "+ cnt+". "+dishName+" (amount: "+amount+") ";
        }
        return output;
    }

}
