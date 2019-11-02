package duke.list;

import duke.exception.DukeException;
import duke.order.Order;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TodayTodoList {
    private Map<String, Integer> tasks;

    public TodayTodoList() {
        this.tasks = new LinkedHashMap<>();
    }

    public TodayTodoList(List<Order> orderList) {
        this.tasks = new LinkedHashMap<>();
        for (Order order: orderList) {
            addTodoFromOrder(order);
        }
    }

    public void addTodo(String dish, int addAmount) {
        if (!tasks.containsKey(dish)) tasks.put(dish, addAmount);
        else {
            int oldAmount = tasks.get(dish);
            tasks.put(dish, oldAmount+addAmount);
        }
    }

    public void deleteTodo(String dish, int delAmount) throws DukeException {
        if (!tasks.containsKey(dish)) { throw new DukeException("Error when deleting the task: cannot find dishes in todo list"); }
        int oldAmount = tasks.get(dish);
        if ((oldAmount-delAmount)<0) { throw new DukeException("Error when deleting the task: removing amount exceed actual"); }
        else if (oldAmount==delAmount) tasks.remove(dish);
        else tasks.put(dish, oldAmount-delAmount);
    }

    public void addTodoFromOrder(Order order) {
        if (!order.isDone()) {
            for (Map.Entry<String, Integer> entry : order.getOrderContent().entrySet()) {
                String dishName = entry.getKey();
                int amount = entry.getValue();
                addTodo(dishName, amount);
            }
        }
    }

    public void deleteTodoFromOrder(Order order) throws DukeException {
        if (!order.isDone()) System.out.println("\t Warning: Deleting undone dishes from todo list");
        for (Map.Entry<String, Integer> entry : order.getOrderContent().entrySet()) {
            String dishName = entry.getKey();
            int amount = entry.getValue();
            deleteTodo(dishName, amount);
        }
    }

    public String toString() {
        String output="\t Task list";
        for (Map.Entry<String, Integer> entry : tasks.entrySet()) {
            String dishName = entry.getKey();
            int amount = entry.getValue();
            output+="\n\t "+dishName+" (amount: "+amount+") ";
        }
        return output;
    }

}
