package duke.command.orderCommand;

import duke.command.Cmd;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Map;

/**
 * Represents a specific {@link Cmd} used to alter the {@link Order} serving date.
 */
public class AlterDateCommand extends Cmd<Order> {

    private int orderNb;
    private String date;

    /**
     * The constructor method for {@link AlterDateCommand}.
     *
     * @param number order number
     * @param newDate new serving date of the {@link Order}
     */
    public AlterDateCommand(int number, String newDate) {
        this.orderNb = number;
        this.date = newDate;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderNb < orderList.size() && orderNb >= 0) {
            if (orderList.getEntry(orderNb).isDone()) {
                throw new DukeException("Order done already. No alteration is expected.");
            }
            Order order = orderList.getEntry(orderNb);
            order.setDate(date);

            //Need update when the dish list updates
            // if this is today's order, then need to update to do list(menu)
            if (order.isToday()) {
                // Need to update after menu is created
                Dish dish;
                int amount;
                for (Map.Entry<Dish, Integer> entry : order.getOrderContent().entrySet()) {
                    dish = entry.getKey();
                    amount = entry.getValue();
                    int dishIndex;
                    for (int i=0; i<amount ;i++) {
                        //decrement dish amount from the menu
                        //new AddDishCommand(dishIndex)
                    }
                }
            }

            ui.showOrderChangedDate(date,orderList.getEntry(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Please enter a valid order number between 1 and "
                    + orderList.size() + " to alter the serving date");
        }
    }
}

