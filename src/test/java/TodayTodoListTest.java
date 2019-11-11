import duke.exception.DukeException;
import duke.order.Order;
import duke.list.TodayTodoList;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Represents a test class for today's Todo List,
 * used to test the methods implemented in the {@link TodayTodoList} class
 *
 * @author VirginiaYu
 */

public class TodayTodoListTest {

    @Test
    public void addTodoFromOrder() {
        TodayTodoList todoList = new TodayTodoList();
        Order order = new Order();
        order.addDish("beef noodle");
        order.addDish("laksa", 2);
        todoList.addTodoFromOrder(order);
        assertEquals("\t Today Task list (" + new Date() + ")" +
                "\n\t 1. beef noodle (amount: 1) \n" +
                "\t 2. laksa (amount: 2) ", todoList.toString());
    }

    @Test
    public void addTodo() {
        TodayTodoList todoList = new TodayTodoList();
        Order order = new Order();
        todoList.addTodo("laksa", 2);
        todoList.addTodo("dumplings",1);
        assertEquals("\t Today Task list (" + new Date() + ")" +
                "\n\t 1. laksa (amount: 2) \n" +
                "\t 2. dumplings (amount: 1) ", todoList.toString());
    }

    @Test
    public void deleteTodoFromOrder() throws DukeException {
        TodayTodoList todoList = new TodayTodoList();
        Order order = new Order();
        order.addDish("dumplings");
        todoList.addTodo("laksa", 2);
        todoList.addTodo("dumplings",1);
        todoList.deleteTodoFromOrder(order);
        assertEquals("\t Today Task list (" + new Date() + ")" +
                "\n\t 1. laksa (amount: 2)", todoList.toString());
    }

    @Test
    public void deleteTodo() throws DukeException {
        TodayTodoList todoList = new TodayTodoList();
        todoList.addTodo("laksa", 2);
        todoList.addTodo("dumplings",1);
        todoList.deleteTodo("laksa", 1);
        assertEquals("\t Today Task list (" + new Date() + ")" +
                "\n\t 1. laksa (amount: 1) \n" +
                "\t 2. dumplings (amount: 1) ", todoList.toString());
    }

}
