package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.DoneCommand;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Map;

/**
 * Represents a specific {@link Cmd} used to mark a {@link Order} as done.
 */
public class DoneOrderCommand extends DoneCommand<Order> {
    private int orderNb;

    /**
     * the constructor method of {@link DoneOrderCommand}
     *
     * @param index order number in the order list
     */
    public DoneOrderCommand(int index) {
        super(index);
        this.orderNb = index;
    }

    @Override
    public void execute(GenericList<Order> orderList, Ui ui, Storage storage) throws DukeException {
        if (orderList.size()==0) {
            throw new DukeException("No order in the list! No order can be done!");
        }
        if (orderNb < orderList.size() && orderNb >= 0) {
            Order doneOrder = orderList.getEntry(orderNb-1);
            if (doneOrder.isDone()) {
                throw new DukeException("Order "+orderNb+" has already be done!");
            }

            // Need to update after menu is created
            Dish dish;
            int amount;
            for (Map.Entry<Dish, Integer> entry : doneOrder.getOrderContent().entrySet()) {
                dish = entry.getKey();
                amount = entry.getValue();
                int dishIndex;
                for (int i=0; i<amount ;i++) {
                    //decrement dish amount from the menu
                    //new DeleteDishCommand(dishIndex)
                }
            }

            ((OrderList)orderList).markOrderDone(orderNb);
            ui.showMarkDone(orderList.getEntry(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Please enter a valid order number after done, between 1 and " + orderList.size());
        }
    }
}
