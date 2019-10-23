package duke.command.orderCommand;

import duke.command.Cmd;
import duke.command.ingredientCommand.DoneCommand;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.order.Order;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

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
            (( OrderList)orderList).markOrderDone(orderNb);
            ui.showMarkDone(orderList.getEntry(orderNb).toString());
            storage.changeContent(orderNb);
        } else {
            throw new DukeException("Please enter a valid order number after done, between 1 and " + orderList.size());
        }
    }
}
