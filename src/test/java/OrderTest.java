import duke.exception.DukeException;
import duke.order.Order;
import duke.parser.Convert;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Represents a test class for order component, used to test the methods implemented in the {@link Order} class
 *
 * @author VirginiaYu
 */

public class OrderTest {

    @Test
    public void addDishToOrder() {
        Order orderA = new Order();
        orderA.addDish("beef noodle");
        orderA.addDish("laksa", 2);
        String s = orderA.toString();
        assertEquals("[✗] Order today \n" +
                "\t    (1) beef noodle 1\n" +
                "\t    (2) laksa 2", s);
    }

    @Test
    public void markOrderDone() {
        Order orderB = new Order();
        orderB.addDish("beef noodle");
        orderB.addDish("laksa", 2);
        orderB.addDish("cake", 1);
        orderB.markAsDone();
        String s = orderB.toString();
        assertEquals("[✓] Order today \n" +
                "\t    (1) beef noodle 1\n" +
                "\t    (2) laksa 2\n" +
                "\t    (3) cake 1", s);
    }

    @Test
    public void isTodayOrder() throws DukeException {
        Order order = new Order();
        order.addDish("beef noodle");
        boolean isToday = order.isToday();
        assertEquals(true, isToday);
        String dateStr = "13/12/2019";
        Date setDate = Convert.stringToDate(dateStr);
        order.setDate(setDate);
        isToday = order.isToday();
        assertEquals(false, isToday);
    }

    @Test
    public void hasDishInOrder() {
        Order order = new Order();
        order.addDish("beef noodle");
        assertEquals(true, order.hasDishes("beef noodle"));
        assertEquals(false, order.hasDishes("cake"));
        order.addDish("cake", 1);
        assertEquals(true, order.hasDishes("beef noodle"));
        assertEquals(true, order.hasDishes("cake"));
    }

    @Test
    public void getDishAmount() {
        Order order = new Order();
        order.addDish("beef noodle");
        order.addDish("cake", 1);
        order.addDish("laksa", 3);
        assertEquals(1, order.getDishesAmount("beef noodle"));
        assertEquals(1, order.getDishesAmount("cake"));
        assertEquals(3, order.getDishesAmount("laksa"));
    }


}
